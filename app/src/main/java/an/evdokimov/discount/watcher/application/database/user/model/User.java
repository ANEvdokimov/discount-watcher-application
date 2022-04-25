package an.evdokimov.discount.watcher.application.database.user.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @PrimaryKey
    public Long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "login")
    public String login;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "token")
    public String token;

    @ColumnInfo(name = "token_generation_time")
    public LocalDateTime tokenGenerationTime;

    @ColumnInfo(name = "is_active", defaultValue = "true")
    public boolean isActive;
}

