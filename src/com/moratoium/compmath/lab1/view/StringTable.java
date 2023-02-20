package com.moratoium.compmath.lab1.view;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface StringTable extends Serializable {

    List<String> getTitles();

    List<Map<String, String>> getTable();


}
