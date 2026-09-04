package migrations

import (
	"os"
	"path/filepath"
	"sort"
	"strings"
)

// List returns the SQL migration scripts in dir, ordered by filename.
func List(dir string) ([]string, error) {
	entries, err := os.ReadDir(dir)
	if err != nil {
		return nil, err
	}

	files := make([]string, 0, len(entries))
	for _, entry := range entries {
		if entry.IsDir() || !strings.HasSuffix(entry.Name(), ".sql") {
			continue
		}
		files = append(files, entry.Name())
	}
	sort.Strings(files)

	return files, nil
}

func Read(dir, name string) (string, error) {
	content, err := os.ReadFile(filepath.Join(dir, name))
	if err != nil {
		return "", err
	}
	return string(content), nil
}

// Directory returns the migrations directory relative to the current working directory.
func Directory() (string, error) {
	workingDirectory, err := os.Getwd()
	if err != nil {
		return "", err
	}
	return filepath.Join(workingDirectory, "db", "migrations"), nil
}
