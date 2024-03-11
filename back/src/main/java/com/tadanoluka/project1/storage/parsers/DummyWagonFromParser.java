package com.tadanoluka.project1.storage.parsers;

import lombok.*;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
class DummyWagonFromParser {
    private final long wagonId;
    private final int destinationStationEsr;
    private final int freightEtsng;
    private final int consigneeId;
    private final int weight;
}
