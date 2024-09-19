package org.mlorenzo.apifirst.apifirstserver.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dimensions {

    @NotNull
    @Min(1) @Max(999)
    private Integer length;

    @NotNull
    @Min(1) @Max(999)
    private Integer width;

    @NotNull
    @Min(1) @Max(999)
    private Integer height;
}
