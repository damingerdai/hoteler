package files

func Kebabcase(s string) string {
	sl := make([]rune, 0, len(s)*2)
	for i, v := range s {
		if v >= 65 && v <= 90 {
			if i == 0 {
				sl = append(sl, v+32)
			} else {
				sl = append(sl, 95, v+32)
			}
		} else {
			sl = append(sl, v)
		}
	}

	return string(sl)
}
