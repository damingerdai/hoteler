# Go parameters
GOCMD=go
GOBUILD=$(GOCMD) build
GOCLEAN=$(GOCMD) clean
GOTEST=$(GOCMD) test
GOGET=$(GOCMD) get
GORUN=${GOCMD} run

TEST?=./...
TEST_EXCLUDE=/mocks/
GOFMT_FILES?=$$(find . -not -path "./vendor/*" -type f -name '*.go')
export ENV_FILE_PATH := $(PWD)/.env

.PHONY: clean
clean:
	rm hoteler || :

build: clean
	go build -o hoteler main.go

run: build
	./hoteler

fmt:
	gofmt -w $(GOFMT_FILES)