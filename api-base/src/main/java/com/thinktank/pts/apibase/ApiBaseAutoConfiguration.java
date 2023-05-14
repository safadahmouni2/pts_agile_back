package com.thinktank.pts.apibase;

import org.springframework.context.annotation.Import;

import com.thinktank.pts.apibase.graphql.CustomGraphQLConfig;
import com.thinktank.pts.apibase.rest.RestTemplateConfig;

@Import({ CustomGraphQLConfig.class, RestTemplateConfig.class })
public class ApiBaseAutoConfiguration {

}