package times

import "testing"

func TestNewTimeVersion(t *testing.T) {
	timeVersion, err := NewTimeVersion()
	if err != nil {
		t.Error(err)
		t.Fail()
	} else {
		t.Log(timeVersion)
	}
}
