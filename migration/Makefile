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

bazel-cmd = bazel
# bazel-targets = $(shell bazel query "kind('go_binary', //cmd/...)" --output=label)
# bazel-targets-names := $(foreach n,$(bazel-targets),$(n))

bazel-clean:
	bazel clean

gazelle: bazel-clean
	 bazel run //:gazelle
gazelle-update-repos: bazel-clean
	bazel run //:gazelle-update-repos

bazel: bazel-clean
	@$(foreach target,$(shell bazel query "kind('go_binary', //...)" --output=label),\
		${bazel-cmd} build ${target} \
	;)