package telegram.menu.exchange;

import telegram.QueryDefiner;

public enum DefaultMenu implements QueryDefiner {
    GET_RATE,
    SETTINGS;

    @Override
    public boolean isQuery(String query) {
        return query.equals(this.name());
    }
}
