#!/usr/bin/env node
/**
 * @file Release and Version Management Script
 * Release tags serve as the single source of truth; no frontend dependencies needed.
 */

import { execFileSync } from 'node:child_process';
import { readFileSync, writeFileSync, appendFileSync } from 'node:fs';
import { resolve } from 'node:path';
import { pathToFileURL } from 'node:url';
import { parseArgs } from 'node:util';

// --- Configuration & Constants ---

const VERSION_REGEX = /^v?(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)(?:-(alpha|beta|rc)\.(0|[1-9]\d*))?$/;
const VALID_INCREMENTS = ['patch', 'minor', 'major', 'prepatch', 'preminor', 'premajor', 'prerelease'];
const VALID_PREIDS = ['alpha', 'beta', 'rc'];

// --- Helper Functions ---

const parseTag = (tag) => {
  const match = VERSION_REGEX.exec(tag);
  if (!match) return null;
  return {
    core: [Number(match[1]), Number(match[2]), Number(match[3])],
    preid: match[4],
    number: Number(match[5]),
  };
};

const compareVersions = (a, b) => {
  return a[0] - b[0] || a[1] - b[1] || a[2] - b[2];
};

/**
 * Calculates the next semantic version tag based on existing git tags and increment type.
 * @param {string} increment
 * @param {string} preid
 * @param {string[]} tags
 * @returns {string}
 */
export function nextVersion(increment, preid = 'beta', tags = []) {
  if (!VALID_INCREMENTS.includes(increment)) {
    throw new Error(`Unsupported version increment: "${increment}"`);
  }
  if (!VALID_PREIDS.includes(preid)) {
    throw new Error(`Unsupported prerelease identifier: "${preid}"`);
  }

  const parsedVersions = tags.map(parseTag).filter(Boolean);

  // Find the latest stable core version (default to 0.0.0 if none exists)
  const latestStableCore = parsedVersions
      .filter(v => !v.preid)
      .map(v => v.core)
      .sort(compareVersions)
      .at(-1) ?? [0, 0, 0];

  let core = [...latestStableCore];

  if (increment === 'prerelease') {
    core = parsedVersions
        .filter(v => v.preid && compareVersions(v.core, latestStableCore) > 0)
        .map(v => v.core)
        .sort(compareVersions)
        .at(-1) ?? [latestStableCore[0], latestStableCore[1], latestStableCore[2] + 1];
  } else if (increment.endsWith('major')) {
    core = [latestStableCore[0] + 1, 0, 0];
  } else if (increment.endsWith('minor')) {
    core = [latestStableCore[0], latestStableCore[1] + 1, 0];
  } else {
    core[2]++;
  }

  let versionStr = core.join('.');

  if (increment.startsWith('pre')) {
    const existingPrereleaseNumbers = parsedVersions
        .filter(v => v.preid === preid && compareVersions(v.core, core) === 0)
        .map(v => v.number);

    const nextPrereleaseNum = Math.max(-1, ...existingPrereleaseNumbers) + 1;
    versionStr += `-${preid}.${nextPrereleaseNum}`;
  }

  const generatedTag = `v${versionStr}`;
  if (tags.includes(generatedTag) || tags.includes(versionStr)) {
    throw new Error(`Tag "${generatedTag}" already exists in the repository.`);
  }

  return generatedTag;
}

/**
 * Prepares the codebase for release by updating version strings in configuration files.
 * @param {string} tag
 * @param {string} root
 */
export function prepare(tag, root = '.') {
  if (!parseTag(tag)) {
    throw new Error(`Invalid release tag format: "${tag}"`);
  }

  const version = tag.replace(/^v/, '');
  const pendingChanges = new Map();

  // Target files and their corresponding match/replacement rules
  const targetFiles = [
    ['build.gradle', /^(version\s*=\s*)['"][^'"]+['"]$/gm, `$1'${version}'`],
    ['pom.xml', /(<artifactId>\s*hoteler\s*<\/artifactId>\s*<version>)[^<]+(<\/version>)/g, `$1${version}$2`],
  ];

  // Read and validate all files before writing any modifications
  for (const [file, regex, replacement] of targetFiles) {
    const filePath = resolve(root, file);
    const content = readFileSync(filePath, 'utf8');

    const matches = [...content.matchAll(regex)];
    if (matches.length !== 1) {
      throw new Error(`Validation failed: Expected exactly one version match in ${file}, found ${matches.length}.`);
    }

    pendingChanges.set(filePath, content.replace(regex, replacement));
  }

  // Commit changes to disk
  for (const [filePath, newContent] of pendingChanges) {
    writeFileSync(filePath, newContent, 'utf8');
  }
}

// --- CLI Execution Entry ---

if (process.argv[1] && import.meta.url === pathToFileURL(resolve(process.argv[1])).href) {
  try {
    const { values } = parseArgs({
      options: {
        increment: { type: 'string' },
        preid: { type: 'string', default: 'beta' },
        'validate-only': { type: 'boolean', default: false },
      },
    });

    if (!values.increment) {
      throw new Error('Missing required argument: --increment <type>');
    }

    const gitTagsOutput = execFileSync('git', ['tag', '--list'], { encoding: 'utf8' });
    const tags = gitTagsOutput.trim().split('\n').filter(Boolean);

    const nextTag = nextVersion(values.increment, values.preid, tags);

    if (!values['validate-only']) {
      prepare(nextTag);
    }

    if (process.env.GITHUB_OUTPUT) {
      appendFileSync(process.env.GITHUB_OUTPUT, `tag=${nextTag}\n`);
    }

    console.log(nextTag);
  } catch (error) {
    console.error(`[Release Error]: ${error.message}`);
    process.exitCode = 1;
  }
}