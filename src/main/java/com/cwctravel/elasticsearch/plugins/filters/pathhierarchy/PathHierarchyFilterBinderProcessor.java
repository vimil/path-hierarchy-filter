package com.cwctravel.elasticsearch.plugins.filters.pathhierarchy;

import org.elasticsearch.index.analysis.AnalysisModule;

public class PathHierarchyFilterBinderProcessor extends AnalysisModule.AnalysisBinderProcessor {
	@Override
	public void processTokenFilters(TokenFiltersBindings tokenFiltersBindings) {
		tokenFiltersBindings.processTokenFilter("path_hierarchy", PathHierarchyTokenFilterFactory.class);
	}
}
