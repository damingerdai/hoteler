import { test } from 'node:test';
import assert from 'node:assert/strict';
import { execFileSync, spawnSync } from 'node:child_process';
import { cpSync, mkdtempSync, readFileSync, rmSync, writeFileSync } from 'node:fs';
import { tmpdir } from 'node:os';
import { join } from 'node:path';
import { fileURLToPath } from 'node:url';

const root = fileURLToPath(new URL('../', import.meta.url));
const chart = join(root, 'deplyoments/helm');
const helm = (...args) => execFileSync('helm', args, { encoding: 'utf8' });

test('default and customized chart render install prerequisites', () => {
  const defaults = helm('template', 'staging', chart);
  assert.match(defaults, /hoteler-api:v0\.0\.7/);
  assert.match(defaults, /hoteler-web:v0\.0\.7/);
  assert.match(defaults, /flyway:\n\s+enabled: false\n\s+baseline-on-migrate: false/);
  assert.doesNotMatch(defaults, /imagePullSecrets:/);
  const custom = helm('template', 'staging', chart, '--namespace', 'staging',
    '--set', 'imagePullSecrets[0].name=ghcr-pull', '--set', 'flyway.enabled=true',
    '--set', 'secret.existingSecret=external-secret', '--set', 'image.tag=', '--set', 'image.web.tag=');
  assert.equal((custom.match(/imagePullSecrets:\n\s+- name: ghcr-pull/g) || []).length, 2);
  assert.match(custom, /flyway:\n\s+enabled: true\n\s+baseline-on-migrate: false/);
  assert.match(custom, /hoteler-api:v0\.0\.7/);
  assert.match(custom, /hoteler-web:v0\.0\.7/);
  assert.doesNotMatch(custom, /kind: Secret\n/);
});

test('packaged chart excludes local credentials and renders successfully', t => {
  const dir = mkdtempSync(join(tmpdir(), 'hoteler-helm-'));
  t.after(() => rmSync(dir, { recursive: true, force: true }));
  const source = join(dir, 'chart');
  cpSync(chart, source, { recursive: true });
  for (const name of ['my-values.yaml', 'my-values-prod.yml', 'old-chart.tgz']) {
    writeFileSync(join(source, name), 'PRIVATE_TEST_VALUE');
  }
  helm('lint', source);
  helm('package', source, '--destination', dir);
  const version = readFileSync(join(source, 'Chart.yaml'), 'utf8').match(/^version: (.+)$/m)[1];
  const archive = join(dir, `hoteler-chart-${version}.tgz`);
  const files = execFileSync('tar', ['-tzf', archive], { encoding: 'utf8' });
  assert.doesNotMatch(files, /my-values|old-chart|TODO\.md/);
  assert.match(files, /hoteler-chart\/README\.md/);
  helm('lint', archive);
  assert.match(helm('template', 'packaged', archive), /name: packaged-api/);
});

test('publishing refuses version mismatch before registry access', () => {
  const result = spawnSync('bash', ['scripts/push-helm-chart.sh', '999.0.0'], {
    cwd: root, encoding: 'utf8',
    env: { ...process.env, GITHUB_USERNAME: 'test', GHCR_TOKEN: 'test' },
  });
  assert.equal(result.status, 1);
  assert.match(result.stderr, /does not match Chart.yaml/);
  assert.doesNotMatch(result.stdout, /Logging in|Pushing/);
});
