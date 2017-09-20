/*
 * Copyright 2017 trivago GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.trivago.rta.features;

import com.trivago.rta.vo.SingleScenario;
import com.trivago.rta.vo.Step;

import java.util.Date;
import java.util.List;

public class FeatureFileContentRenderer {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    /**
     * Get the complete content that can be written to a valid feature file.
     *
     * @return the feature file content.
     */
    String getRenderedFeatureFileContent(SingleScenario singleScenario) {
        StringBuilder renderedContent = new StringBuilder();

        addTags(renderedContent, singleScenario.getFeatureTags());
        addFeatureName(renderedContent, singleScenario.getFeatureName());

        addTags(renderedContent, singleScenario.getScenarioTags());
        addScenarioName(renderedContent, singleScenario.getScenarioName());

        addSteps(renderedContent, singleScenario.getBackgroundSteps());
        addSteps(renderedContent, singleScenario.getSteps());

        addGeneratedByCucumberMessage(renderedContent);

        return renderedContent.toString();
    }

    private void addGeneratedByCucumberMessage(final StringBuilder stringBuilder) {
        stringBuilder.append(System.lineSeparator())
                .append("# Generated by Cucable, ").append(new Date())
                .append(LINE_SEPARATOR);
    }

    private void addSteps(final StringBuilder stringBuilder, final List<Step> steps) {
        for (Step step : steps) {
            stringBuilder.append(step.getName()).append(LINE_SEPARATOR);
            if (!step.getDataTableString().isEmpty()) {
                stringBuilder
                        .append(formatDataTableString(step.getDataTableString()))
                        .append(LINE_SEPARATOR);
            }
        }
    }

    private void addScenarioName(final StringBuilder stringBuilder, final String scenarioName) {
        stringBuilder
                .append("Scenario: ").append(scenarioName)
                .append(System.lineSeparator()).append(LINE_SEPARATOR);
    }

    private void addFeatureName(final StringBuilder stringBuilder, final String featureName) {
        stringBuilder
                .append("Feature: ").append(featureName)
                .append(System.lineSeparator()).append(LINE_SEPARATOR);
    }

    private void addTags(final StringBuilder stringBuilder, final List<String> featureTags) {
        for (String tag : featureTags) {
            stringBuilder.append(tag).append(LINE_SEPARATOR);
        }
    }

    private String formatDataTableString(final String dataTableString) {
        String[] dataTableRows = dataTableString.split("\\|\\|");
        return "  " + String.join("|" + LINE_SEPARATOR + "  |", dataTableRows);
    }
}
