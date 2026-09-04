package cmd

import (
	"fmt"
	"strings"

	"charm.land/bubbletea/v2"
	"github.com/damingerdai/hoteler/migration/internal/migrations"
	"github.com/spf13/cobra"
)

type migrationScript struct {
	name    string
	content string
}

type migrationListModel struct {
	scripts []migrationScript
	cursor  int
	offset  int
	width   int
	height  int
}

func (m migrationListModel) Init() tea.Cmd {
	return nil
}

func (m migrationListModel) Update(message tea.Msg) (tea.Model, tea.Cmd) {
	switch message := message.(type) {
	case tea.KeyPressMsg:
		switch message.String() {
		case "q", "esc", "ctrl+c":
			return m, tea.Quit
		case "up", "k":
			if m.cursor > 0 {
				m.cursor--
				m.offset = 0
			}
		case "down", "j":
			if m.cursor < len(m.scripts)-1 {
				m.cursor++
				m.offset = 0
			}
		case "pgup":
			m.cursor -= m.visibleLines()
			if m.cursor < 0 {
				m.cursor = 0
			}
		case "pgdown":
			m.cursor += m.visibleLines()
			if m.cursor >= len(m.scripts) {
				m.cursor = len(m.scripts) - 1
			}
		case "home":
			m.cursor = 0
		case "end":
			m.cursor = len(m.scripts) - 1
		}
		m.ensureCursorVisible()
	case tea.WindowSizeMsg:
		m.width = message.Width
		m.height = message.Height
		m.ensureCursorVisible()
	}

	return m, nil
}

func (m migrationListModel) View() tea.View {
	var view strings.Builder
	view.WriteString("Database migrations — select a script to view its SQL\n\n")

	if len(m.scripts) == 0 {
		view.WriteString("No SQL migration scripts found.\n")
	} else {
		leftWidth := m.listWidth()
		rightWidth := m.width - leftWidth - 3
		if rightWidth < 1 {
			rightWidth = 1
		}
		leftEnd := m.offset + m.visibleLines()
		if leftEnd > len(m.scripts) {
			leftEnd = len(m.scripts)
		}
		rightLines := strings.Split(strings.ReplaceAll(m.scripts[m.cursor].content, "\r\n", "\n"), "\n")
		for row := 0; row < m.visibleLines(); row++ {
			index := m.offset + row
			prefix := "  "
			name := ""
			if index < leftEnd {
				name = m.scripts[index].name
			}
			if index == m.cursor {
				prefix = "> "
			}
			left := prefix + truncate(name, leftWidth-2)
			right := ""
			if row < len(rightLines) {
				right = truncate(rightLines[row], rightWidth)
			}
			fmt.Fprintf(&view, "%-*s | %s\n", leftWidth, left, right)
		}
	}

	view.WriteString("\n↑/↓ or j/k to select · q or esc to quit")
	result := tea.NewView(view.String())
	result.AltScreen = true
	return result
}

func (m migrationListModel) visibleLines() int {
	visible := m.height - 5
	if visible < 1 {
		return 1
	}
	return visible
}

func (m migrationListModel) listWidth() int {
	width := 38
	if m.width > 0 && m.width < 90 {
		width = m.width / 2
	}
	if width < 12 {
		width = 12
	}
	return width
}

func truncate(value string, width int) string {
	if width <= 0 {
		return ""
	}
	runes := []rune(value)
	if len(runes) <= width {
		return value
	}
	return string(runes[:width-1]) + "…"
}

func (m *migrationListModel) ensureCursorVisible() {
	if len(m.scripts) == 0 {
		m.cursor = 0
		m.offset = 0
		return
	}
	if m.cursor < 0 {
		m.cursor = 0
	}
	if m.cursor >= len(m.scripts) {
		m.cursor = len(m.scripts) - 1
	}
	visible := m.visibleLines()
	if m.cursor < m.offset {
		m.offset = m.cursor
	}
	if m.cursor >= m.offset+visible {
		m.offset = m.cursor - visible + 1
	}
}

func runMigrationList() error {
	directory, err := migrations.Directory()
	if err != nil {
		return err
	}
	files, err := migrations.List(directory)
	if err != nil {
		return fmt.Errorf("read migrations directory %q: %w", directory, err)
	}

	scripts := make([]migrationScript, 0, len(files))
	for _, file := range files {
		content, readErr := migrations.Read(directory, file)
		if readErr != nil {
			return fmt.Errorf("read migration script %q: %w", file, readErr)
		}
		scripts = append(scripts, migrationScript{name: file, content: content})
	}

	_, err = tea.NewProgram(migrationListModel{scripts: scripts, width: 120, height: 24}).Run()
	return err
}

var migrateListCmd = &cobra.Command{
	Use:   "list",
	Short: "交互式显示数据库迁移脚本",
	Args:  cobra.NoArgs,
	RunE: func(command *cobra.Command, args []string) error {
		return runMigrationList()
	},
}

func init() {
	migrateCmd.AddCommand(migrateListCmd)
}
