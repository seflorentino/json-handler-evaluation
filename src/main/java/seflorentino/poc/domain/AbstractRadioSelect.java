package seflorentino.poc.domain;

public class AbstractRadioSelect {

    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public AbstractRadioSelect setSelected(boolean selected) {
        this.selected = selected;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractRadioSelect that = (AbstractRadioSelect) o;

        return selected == that.selected;
    }

    @Override
    public int hashCode() {
        return (selected ? 1 : 0);
    }
}
