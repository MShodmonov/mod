package uz.mod.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectorPayload {

    private UUID conceptionId;

    private UUID subjectId;

    private UUID detailId;
}
