package com.thinktank.pts.apibase.graphql.output;

import java.util.List;

import lombok.Data;

@Data
public class CollectionResult<T> {

	private List<T> items;
}
