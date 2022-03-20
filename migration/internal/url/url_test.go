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
	"testing"
)

func TestSchemeFromUrl(t *testing.T) {
	cases := []struct {
		name      string
		urlStr    string
		expected  string
		expectErr error
	}{
		{
			name:     "Simple",
			urlStr:   "protocol://path",
			expected: "protocol",
		},
		{
			// See issue #264
			name:     "MySQLWithPort",
			urlStr:   "mysql://user:pass@tcp(host:1337)/db",
			expected: "mysql",
		},
		{
			name:      "Empty",
			urlStr:    "",
			expectErr: errEmptyURL,
		},
		{
			name:      "NoScheme",
			urlStr:    "hello",
			expectErr: errNoScheme,
		},
	}

	for _, tc := range cases {
		t.Run(tc.name, func(t *testing.T) {
			s, err := SchemeFromURL(tc.urlStr)
			if err != tc.expectErr {
				t.Fatalf("expected %q, but received %q", tc.expectErr, err)
			}
			if s != tc.expected {
				t.Fatalf("expected %q, but received %q", tc.expected, s)
			}
		})
	}
}
