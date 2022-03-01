package wg.application.controller;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/*************************************************************
 * @Package wg.application.controller
 * @author wg
 * @date 2021/2/18 11:08
 * @version
 * @Copyright
 *************************************************************/
public class RorschachDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {


        return null;
    }
}
