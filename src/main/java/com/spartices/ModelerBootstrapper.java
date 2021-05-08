package com.spartices;

import java.util.List;

import org.flowable.ui.modeler.domain.AbstractModel;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.properties.FlowableModelerAppProperties;
import org.flowable.ui.modeler.repository.ModelRepository;
import org.flowable.ui.modeler.repository.ModelSort;
import org.flowable.ui.modeler.service.DecisionTableModelConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Responsible for executing all action required after booting up the Spring container.
 *
 * @author Yvo Swillens
 */
@Component
public class ModelerBootstrapper implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    protected ModelRepository modelRepository;

    @Autowired
    private FlowableModelerAppProperties modelerAppProperties;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) { // Using Spring MVC, there are multiple child contexts. We only care about the root

            if (modelerAppProperties == null || modelerAppProperties.isDecisionTableMigrationEnabled()) {
                migrateDecisionTables();
            }
        }
    }

    public void migrateDecisionTables() {
        List<Model> decisionTableModels = modelRepository.findByModelType(AbstractModel.MODEL_TYPE_DECISION_TABLE, ModelSort.NAME_ASC);

        decisionTableModels.forEach(decisionTableModel -> {
            if (DecisionTableModelConversionUtil.convertModelToV3(decisionTableModel)) {
                modelRepository.save(decisionTableModel);
            }
        });
    }
}
