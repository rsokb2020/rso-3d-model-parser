package si.fri.rso.kb6750.model3dparser.config;

import javax.enterprise.context.ApplicationScoped;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

@ConfigBundle("rest-properties")
@ApplicationScoped
public class RestProperties {

    @ConfigValue(watch = true)
    private Boolean maintenanceMode;

    private String catalogServiceIp;

    public Boolean getMaintenanceMode() {
        return this.maintenanceMode;
    }

    public void setMaintenanceMode(final Boolean maintenanceMode) {
        this.maintenanceMode = maintenanceMode;
    }

    public String getCatalogServiceIp() {
        return this.catalogServiceIp;
    }

    public void setCatalogServiceIp(String catalogServiceIp) {
        this.catalogServiceIp = catalogServiceIp;
    }
}
