/**
The MIT License (MIT)

Original Work
Copyright (c) 2016 Matthias Kadenbach
https://github.com/mattes/migrate

Modified Work
Copyright (c) 2018 Dale Hui
https://github.com/golang-migrate/migrate


Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
**/
package url

import (
	"errors"
	"strings"
)

var errNoScheme = errors.New("no scheme")
var errEmptyURL = errors.New("URL cannot be empty")

// schemeFromURL returns the scheme from a URL string
func SchemeFromURL(url string) (string, error) {
	if url == "" {
		return "", errEmptyURL
	}

	i := strings.Index(url, ":")

	// No : or : is the first character.
	if i < 1 {
		return "", errNoScheme
	}

	return url[0:i], nil
}
