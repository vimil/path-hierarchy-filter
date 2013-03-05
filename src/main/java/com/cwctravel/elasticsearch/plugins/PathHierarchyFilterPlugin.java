package com.cwctravel.elasticsearch.plugins;

import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.plugins.AbstractPlugin;

import com.cwctravel.elasticsearch.plugins.filters.pathhierarchy.PathHierarchyFilterBinderProcessor;

public class PathHierarchyFilterPlugin extends AbstractPlugin {

	public String description() {
		return "";
	}

	public String name() {
		return "filter-pathhierarchy";
	}

	public void onModule(AnalysisModule module) {
		module.addProcessor(new PathHierarchyFilterBinderProcessor());
	}
}
