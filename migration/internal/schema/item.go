package schema

import "time"

type Item struct {
	Id            int
	Version       string
	Description   string
	Script        string
	Checksum      int64
	ExecutionTime int64
	Success       bool
	InstalledOn   time.Time
}

type Items = []Item
