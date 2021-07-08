package times

import (
	"time"
)

func GetTimeZone(timezoneName string) (*time.Location, error) {
	return time.LoadLocation(timezoneName)
}

func NowWithTimeZone(l *time.Location) time.Time {
	return time.Now().In(l)
}

func TimeVersion(t time.Time) string {
	return t.Format("20060102150405")
}

func NewTimeVersion() (string, error) {
	tz := "UTC"
	location, err := GetTimeZone(tz)
	if err != nil {
		return "", err
	}
	time := NowWithTimeZone(location)

	return TimeVersion(time), nil
}
