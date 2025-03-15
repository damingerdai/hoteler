#!/bin/bash

cd .. && mvn release:prepare -DpushChanges=false && mvn git-changelog-maven-plugin:git-changelog