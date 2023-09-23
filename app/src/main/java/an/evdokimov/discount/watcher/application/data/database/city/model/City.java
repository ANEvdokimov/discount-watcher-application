package an.evdokimov.discount.watcher.application.data.database.city.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City implements Serializable {
    private Long id;
    private String name;
    private String cyrillicName;
}
