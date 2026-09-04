APP := hoteler
GO := go
TEST ?= ./...

.PHONY: all build clean run test fmt

all: build

build:
	$(GO) build -o $(APP) .

clean:
	rm -f -- $(APP)

run: build
	./$(APP)

test:
	$(GO) test $(TEST)

fmt:
	gofmt -w $$(find . -type f -name '*.go' -not -path './vendor/*')
