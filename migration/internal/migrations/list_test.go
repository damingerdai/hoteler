package migrations

import (
	"os"
	"path/filepath"
	"reflect"
	"testing"
)

func TestList(t *testing.T) {
	dir := t.TempDir()
	for _, name := range []string{"002_b.down.sql", "001_a.up.sql", "README.md"} {
		if err := os.WriteFile(filepath.Join(dir, name), nil, 0600); err != nil {
			t.Fatal(err)
		}
	}
	if err := os.Mkdir(filepath.Join(dir, "nested"), 0700); err != nil {
		t.Fatal(err)
	}

	got, err := List(dir)
	if err != nil {
		t.Fatal(err)
	}
	want := []string{"001_a.up.sql", "002_b.down.sql"}
	if !reflect.DeepEqual(got, want) {
		t.Fatalf("List() = %v, want %v", got, want)
	}
}

func TestRead(t *testing.T) {
	dir := t.TempDir()
	name := "001_create_table.up.sql"
	want := "CREATE TABLE users;\n"
	if err := os.WriteFile(filepath.Join(dir, name), []byte(want), 0600); err != nil {
		t.Fatal(err)
	}

	got, err := Read(dir, name)
	if err != nil {
		t.Fatal(err)
	}
	if got != want {
		t.Fatalf("Read() = %q, want %q", got, want)
	}
}
