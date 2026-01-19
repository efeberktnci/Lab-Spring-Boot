CREATE TABLE IF NOT EXISTS notes (
                                     id INTEGER PRIMARY KEY AUTOINCREMENT,
                                     user_id INTEGER NOT NULL,
                                     title TEXT NOT NULL,
                                     content TEXT NOT NULL,
                                     created_at TEXT NOT NULL DEFAULT (datetime('now')),

    CONSTRAINT fk_notes_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE
    );
