// BridgeDb,
// An abstraction layer for identifier mapping services, both local and online.
//
// Copyright 2006-2009  BridgeDb developers
// Copyright 2012-2013  Christian Y. A. Brenninkmeijer
// Copyright 2012-2013  OpenPhacts
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
package org.bridgedb.ws;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bridgedb.DataSource;
import org.bridgedb.Xref;
import org.bridgedb.rdf.UriPattern;
import org.bridgedb.statistics.MappingSetInfo;
import org.bridgedb.statistics.OverallStatistics;
import org.bridgedb.statistics.ProfileInfo;
import org.bridgedb.uri.Mapping;
import org.bridgedb.uri.UriMapper;
import org.bridgedb.utils.BridgeDBException;
import org.bridgedb.ws.bean.DataSourceUriPatternBean;
import org.bridgedb.ws.bean.MappingBean;
import org.bridgedb.ws.bean.MappingSetInfoBean;
import org.bridgedb.ws.bean.MappingSetInfoBeanFactory;
import org.bridgedb.ws.bean.OverallStatisticsBean;
import org.bridgedb.ws.bean.OverallStatisticsBeanFactory;
import org.bridgedb.ws.bean.ProfileBean;
import org.bridgedb.ws.bean.ProfileBeanFactory;
import org.bridgedb.ws.bean.UriPatternBean;
import org.bridgedb.ws.bean.UriSearchBean;
import org.bridgedb.ws.bean.XrefBean;

/**
 *
 * @author Christian
 */
public class WSUriMapper extends WSCoreMapper implements UriMapper{
    
    WSUriInterface uriService;
    private static final ArrayList<String> NO_SYSCODES = null;
    private static final ArrayList<String> NO_URI_PATTERNS = null;
    
    
    public WSUriMapper(WSUriInterface uriService){
        super(uriService);
        this.uriService = uriService;
    }

    @Override
    public Set<Xref> mapID(Xref sourceXref, String profileUri, DataSource... tgtDataSources) throws BridgeDBException {
        Collection<Mapping> beans = mapFull(sourceXref, profileUri, tgtDataSources);
        return extractXref(beans);
    }
    
    private Set<Xref> extractXref(Collection<Mapping> beans){
        HashSet<Xref> results = new HashSet<Xref>();
        for (Mapping bean:beans){
           Xref targetXref = bean.getTarget();
           results.add(targetXref);
        }
        return results;        
    }
    
    @Override
    public Set<Xref> mapID(Xref sourceXref, String profileUri, DataSource tgtDataSource) throws BridgeDBException {
        Collection<Mapping> beans = mapFull(sourceXref, profileUri, tgtDataSource);
        return extractXref(beans);
    }

    @Override
    public Set<Xref> mapID(Xref sourceXref, String profileUri) throws BridgeDBException {
        Collection<Mapping> beans = mapFull(sourceXref, profileUri);
        return extractXref(beans);
    }

    @Override
    public Set<String> mapUri(String sourceUri, String profileUri, UriPattern... tgtUriPatterns) throws BridgeDBException {
        Collection<Mapping> beans = mapFull(sourceUri, profileUri, tgtUriPatterns);
        return extractUris(beans);
     }

    private Set<String> extractUris(Collection<Mapping> beans){
        HashSet<String> results = new HashSet<String>();
        for (Mapping bean:beans){
            results.addAll(bean.getTargetUri());
        }
        return results;          
    }
    
    @Override
    public Set<String> mapUri(Xref sourceXref, String profileUri, UriPattern... tgtUriPatterns) throws BridgeDBException {
        Collection<Mapping> beans = mapFull(sourceXref, profileUri, tgtUriPatterns);
        return extractUris(beans);
    }

    @Override
    public Set<String> mapUri(Xref sourceXref, String profileUri, UriPattern tgtUriPattern) throws BridgeDBException {
        Collection<Mapping> beans = mapFull(sourceXref, profileUri, tgtUriPattern);
        return extractUris(beans);
    }

    @Override
    public Set<String> mapUri(Xref sourceXref, String profileUri) throws BridgeDBException {
        Collection<Mapping> beans = mapFull(sourceXref, profileUri);
        return extractUris(beans);
    }

    @Override
    public Set<String> mapUri(String sourceUri, String profileUri, UriPattern tgtUriPattern) throws BridgeDBException {
        Collection<Mapping> beans = mapFull(sourceUri, profileUri, tgtUriPattern);
        return extractUris(beans);
    }

    @Override
    public Set<String> mapUri(String sourceUri, String profileUri) throws BridgeDBException {
        Collection<Mapping> beans = mapFull(sourceUri, profileUri);
        return extractUris(beans);
    }

    @Override
    public Set<Mapping> mapFull(Xref sourceXref, String profileUri, DataSource... tgtDataSources) 
            throws BridgeDBException {
        if (sourceXref == null){
            return new HashSet<Mapping>();
        }
        if (tgtDataSources == null || tgtDataSources.length == 0){
            return mapFull(sourceXref, profileUri);
        }
        ArrayList<String> tgtSysCodes = new ArrayList<String>();
        for (int i = 0 ; i < tgtDataSources.length; i++){
            if (tgtDataSources[i] != null){
                tgtSysCodes.add(tgtDataSources[i].getSystemCode());
            }
        }
        if (tgtSysCodes.isEmpty()){
            return new HashSet<Mapping>();
        }        
        List<MappingBean> beans = uriService.map(sourceXref.getId(), sourceXref.getDataSource().getSystemCode(), 
                profileUri, tgtSysCodes, NO_URI_PATTERNS);
        HashSet<Mapping> results = new HashSet<Mapping>();
        for (MappingBean bean:beans){
            results.add(MappingBean.asMapping(bean)) ;   
        }
        return results; 
    }
 
    @Override
    public Set<Mapping> mapFull(Xref sourceXref, String profileUri, UriPattern... tgtUriPatterns)
            throws BridgeDBException {
        if (sourceXref == null){
            return new HashSet<Mapping>();
        }
        if (tgtUriPatterns == null || tgtUriPatterns.length == 0){
            return mapFull(sourceXref, profileUri);
        }
        ArrayList<String> tgtUriPatternStrings = new ArrayList<String>();
        for (UriPattern tgtUriPattern:tgtUriPatterns){
            if (tgtUriPattern != null){
                tgtUriPatternStrings.add(tgtUriPattern.getUriPattern());
            }
        }
        if (tgtUriPatternStrings.isEmpty()){
            return new HashSet<Mapping>();
        }
        List<MappingBean> beans = uriService.map(sourceXref.getId(), sourceXref.getDataSource().getSystemCode(), 
                profileUri, NO_SYSCODES, tgtUriPatternStrings);
        HashSet<Mapping> results = new HashSet<Mapping>();
        for (MappingBean bean:beans){
            results.add(MappingBean.asMapping(bean)) ;   
        }
        return results; 
    }
 
    @Override
    public Set<Mapping> mapFull(Xref sourceXref, String profileUri, DataSource tgtDataSource) throws BridgeDBException {
        DataSource[] tgtDataSources = new DataSource[1];
        tgtDataSources[0] = tgtDataSource;

        return mapFull(sourceXref, profileUri, tgtDataSources);
    }

    @Override
    public Set<Mapping> mapFull(Xref sourceXref, String profileUri) throws BridgeDBException {
        if (sourceXref == null){
            return new HashSet<Mapping>();
        }
        List<MappingBean> beans = uriService.map(sourceXref.getId(), sourceXref.getDataSource().getSystemCode(), 
                profileUri, NO_SYSCODES, NO_URI_PATTERNS);
        HashSet<Mapping> results = new HashSet<Mapping>();
        for (MappingBean bean:beans){
            results.add(MappingBean.asMapping(bean)) ;   
        }
        return results; 
    }

    @Override
    public Set<Mapping> mapFull(Xref sourceXref, String profileUri, UriPattern tgtUriPattern) throws BridgeDBException {
        UriPattern[] tgtUriPatterns = new UriPattern[1];
        tgtUriPatterns[0] = tgtUriPattern;
        return mapFull(sourceXref, profileUri, tgtUriPatterns);
    }

    @Override
    public Set<Mapping> mapFull(String sourceUri, String profileUri, DataSource... tgtDataSources) throws BridgeDBException {
        if (tgtDataSources == null || tgtDataSources.length == 0){
            return mapFull(sourceUri, profileUri);
        }
        if (sourceUri == null){
            return new HashSet<Mapping>();
        }
        ArrayList<String> tgtSysCodes = new ArrayList<String>();
        for (int i = 0 ; i < tgtDataSources.length; i++){
            if (tgtDataSources[i] != null){
                tgtSysCodes.add(tgtDataSources[i].getSystemCode());
            }
        }
        if (tgtSysCodes.isEmpty()){
            return new HashSet<Mapping>();
        }
        List<MappingBean> beans = uriService.map(sourceUri, profileUri, tgtSysCodes, NO_URI_PATTERNS);
        HashSet<Mapping> results = new HashSet<Mapping>();
        for (MappingBean bean:beans){
            results.add(MappingBean.asMapping(bean)) ;   
        }
        return results; 
    }

    @Override
    public Set<Mapping> mapFull(String sourceUri, String profileUri, DataSource tgtDataSource) throws BridgeDBException {
        DataSource[] tgtDataSources = new DataSource[1];
        tgtDataSources[0] = tgtDataSource;
        return mapFull(sourceUri, profileUri, tgtDataSources);
    }

    @Override
    public Set<Mapping> mapFull(String sourceUri, String profileUri) throws BridgeDBException {
        if (sourceUri == null){
            return new HashSet<Mapping>();
        }
        List<MappingBean> beans = uriService.map(sourceUri, profileUri, NO_SYSCODES, NO_URI_PATTERNS);
        HashSet<Mapping> results = new HashSet<Mapping>();
        for (MappingBean bean:beans){
            results.add(MappingBean.asMapping(bean)) ;   
        }
        return results; 
    }

    @Override
    public Set<Mapping> mapFull(String sourceUri, String profileUri, UriPattern tgtUriPattern) throws BridgeDBException {
        UriPattern[] tgtUriPatterns = new UriPattern[1];
        tgtUriPatterns[0] = tgtUriPattern;
        return mapFull(sourceUri, profileUri, tgtUriPatterns);
    }

    @Override
    public Set<Mapping> mapFull(String sourceUri, String profileUri, UriPattern... tgtUriPatterns) throws BridgeDBException {
        if (tgtUriPatterns == null || tgtUriPatterns.length == 0){
            return mapFull(sourceUri, profileUri);
        }
        if (sourceUri == null){
            return new HashSet<Mapping>();
        }
        ArrayList<String> tgtUriPatternStrings = new ArrayList<String>();
        for (UriPattern tgtUriPattern:tgtUriPatterns){
            if (tgtUriPattern != null){
                tgtUriPatternStrings.add(tgtUriPattern.getUriPattern());
            }
        }
        if (tgtUriPatternStrings.isEmpty()){
            return new HashSet<Mapping>();
        }
        List<MappingBean> beans = uriService.map(sourceUri, profileUri, NO_SYSCODES, tgtUriPatternStrings);
        HashSet<Mapping> results = new HashSet<Mapping>();
        for (MappingBean bean:beans){
            results.add(MappingBean.asMapping(bean)) ;   
        }
        return results; 
    }
    
    @Override
    public boolean uriExists(String Uri) throws BridgeDBException {
        return uriService.UriExists(Uri).exists();
    }

    @Override
    public Set<String> uriSearch(String text, int limit) throws BridgeDBException {
        UriSearchBean  bean = uriService.UriSearch(text, "" + limit);
        return bean.getUriSet();
    }

    @Override
    public Xref toXref(String Uri) throws BridgeDBException {
        XrefBean bean = uriService.toXref(Uri);
        if (bean == null){
            return null;
        }
        return bean.asXref();
    }

    @Override
    public Mapping getMapping(int id) throws BridgeDBException {
        MappingBean bean =  uriService.getMapping("" + id);
        return MappingBean.asMapping(bean); 
    }

    //@Override Too slow
    //public List<Mapping> getSampleMapping() throws BridgeDBException {
    //    return uriService.getSampleMappings();
    //}
    
    @Override
    public OverallStatistics getOverallStatistics() throws BridgeDBException {
        OverallStatisticsBean bean = uriService.getOverallStatistics();
        return OverallStatisticsBeanFactory.asOverallStatistics(bean);
    }

    @Override
    public MappingSetInfo getMappingSetInfo(int mappingSetId) throws BridgeDBException {
        MappingSetInfoBean bean = uriService.getMappingSetInfo("" + mappingSetId);
        return MappingSetInfoBeanFactory.asMappingSetInfo(bean);
    }

    @Override
    public List<MappingSetInfo> getMappingSetInfos(String sourceSysCode, String targetSysCode) throws BridgeDBException {
        List<MappingSetInfoBean> beans = uriService.getMappingSetInfos(sourceSysCode, targetSysCode);
        ArrayList<MappingSetInfo> results = new ArrayList<MappingSetInfo>(); 
        for (MappingSetInfoBean bean:beans){
            results.add(MappingSetInfoBeanFactory.asMappingSetInfo(bean));
        }
        return results;  
    }
   
    @Override
    public Set<String> getUriPatterns(String dataSource) throws BridgeDBException {
        DataSourceUriPatternBean bean = uriService.getDataSource(dataSource);
        if (bean == null || bean.getUriPatterns() == null){
            System.out.println("failed with " + dataSource);
            System.out.println(bean);
            return new HashSet<String>();
        } else {
            Set<String> results = new HashSet<String>();
            for (UriPatternBean pattern:bean.getUriPatterns()){
                results.add(pattern.getPattern());
            }
            return results;
        }
    }

	@Override
	public List<ProfileInfo> getProfiles() throws BridgeDBException {
		List<ProfileBean> beans = uriService.getProfiles();
		List<ProfileInfo> results = new ArrayList<ProfileInfo>();
		for (ProfileBean bean:beans) {
			results.add(ProfileBeanFactory.asProfileInfo(bean));
		}
		return results;
	}

	@Override
	public ProfileInfo getProfile(String profileURI)
			throws BridgeDBException {
		ProfileBean profile = uriService.getProfile(profileURI);
		ProfileInfo result = ProfileBeanFactory.asProfileInfo(profile);
		return result;
	}
    
    @Override
    public int getSqlCompatVersion() throws BridgeDBException {
        return Integer.parseInt(uriService.getSqlCompatVersion());
    }


  }
