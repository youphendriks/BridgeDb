/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bridgedb.ws.server;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Set;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.bridgedb.IDMapperException;
import org.bridgedb.Xref;
import org.bridgedb.sql.BridgeDbSqlException;
import org.bridgedb.sql.SQLAccess;
import org.bridgedb.sql.URLMapperSQL;
import org.bridgedb.sql.URLSqlFactory;
import org.bridgedb.ws.WSService;

/**
 *
 * @author Christian
 */
public class WsSqlServer extends WSService{
    
    public WsSqlServer() throws BridgeDbSqlException  {
        SQLAccess sqlAccess = URLSqlFactory.createSQLAccess();
        URLMapperSQL urlMapperSQL = new URLMapperSQL(sqlAccess);
        idMapper = urlMapperSQL;
        urlMapper = urlMapperSQL;
        byXrefPosition = urlMapperSQL;
        byURLPosition = urlMapperSQL;
        provenanceMapper = urlMapperSQL;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response welcomeMessage() throws IDMapperException, UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbInnerPure;
        StringBuilder sbInnerEncoded;

        Xref first = byXrefPosition.getXrefByPosition(0);
        Xref second = byXrefPosition.getXrefByPosition(1);
        Set<Xref> firstMaps = idMapper.mapID(first);
        Set<String> keys = idMapper.getCapabilities().getKeys();

        sb.append("<?xml version=\"1.0\"?>");
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" "
                + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
        sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">");
        sb.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\"/>");
        sb.append("<head><title>OPS IMS</title></head><body>");
        sb.append("<h1>Open PHACTS Identity Mapping Service</h1>");
        sb.append("<p>1 Welcome to the prototype Identity Mapping Service. </p>");
        
        sb.append("<p>The Main OPS method is <a href=\"/OPS-IMS/api/#mapByURL\">mapByURL</a></dt>");
        sb.append("<dd>List the URLs that map to this URL</dd>");
        sb.append("<p><a href=\"/OPS-IMS/api\">API Page</a></p>");
        sb.append("</body></html>");
        return Response.ok(sb.toString(), MediaType.TEXT_HTML).build();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/api")
    public Response apiPage() throws IDMapperException, UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
 
        Xref first = byXrefPosition.getXrefByPosition(0);
        Xref second = byXrefPosition.getXrefByPosition(1);
        Set<Xref> firstMaps = idMapper.mapID(first);
        Set<String> keys = idMapper.getCapabilities().getKeys();

        sb.append("<?xml version=\"1.0\"?>");
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" "
                + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
        sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">");
        sb.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\"/>");
        sb.append("<head><title>OPS IMS</title></head><body>");
        sb.append("<h1>Open PHACTS Identity Mapping Service</h1>");
        sb.append("<p><a href=\"/OPS-IMS\">Home Page</a></p>");
                
        sb.append("<p>");
        sb.append("Support services include:");
        sb.append("<dl>");

        
        introduce_URLMapper(sb);
        if (byURLPosition != null){ 
            sb.append("<dt><a href=\"#getURLByposition\">getURLByposition</a></dt>");
            sb.append("<dd>Returns the URL(s) currently at this position.</dd>");
        }
        if (byXrefPosition != null){ 
            sb.append("<dt><a href=\"#getXrefByposition\">getXrefByposition</a></dt>");
            sb.append("<dd>Returns the Xref(s) currently at this position.</dd>");
        }
        introduce_IDMapper(sb);
        introduce_IDMapperCapabilities(sb, keys);
        introduce_Statistics(sb);
        sb.append("</dl>");
        sb.append("</p>");
                
        
        describe_URLMapper(sb, first,  firstMaps, second);            
        describe_IDMapper(sb, first, firstMaps, second);
        describe_IDMapperCapabilities(sb, first, firstMaps, keys);
        describe_byXrefPosition(sb, first);
        describe_getURLByposition(sb, first);
        
        sb.append("</body></html>");
        return Response.ok(sb.toString(), MediaType.TEXT_HTML).build();
    }
        
    @Path("/BridgeDB")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response bridgeDBPage() throws IDMapperException, UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
 
        Xref first = byXrefPosition.getXrefByPosition(0);
        Xref second = byXrefPosition.getXrefByPosition(1);
        Set<Xref> firstMaps = idMapper.mapID(first);
        Set<String> keys = idMapper.getCapabilities().getKeys();

        sb.append("<?xml version=\"1.0\"?>");
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" "
                + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
        sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">");
        sb.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\"/>");
        sb.append("<head><title>BridgeDD</title></head><body>");
        sb.append("<h1>BridgeDB WebService</h1>");
        sb.append("<p>");
        sb.append("<a href=\"/OPS-IMS\">Home Page</a>");
        sb.append("<a href=\"/OPS-IMS/api\">All Services</a>");
        sb.append("</p>");
                
        sb.append("<p>");
        sb.append("The methods shown on this page work with any BridgeDB IDMapper. ");
        sb.append("The URL ones require a thin wrapper and a few minor changes to the Core BridgeDB. ");
        sb.append("The free search method depend on the underlyimg IDMapper support free search. Core BridgeDB. ");
        sb.append("</p>");
        sb.append("<p>");
        sb.append("Support services include:");
        sb.append("<dl>");

        
        introduce_URLMapper(sb);
        if (byURLPosition != null){ 
            sb.append("<dt><a href=\"#getURLByposition\">getURLByposition</a></dt>");
            sb.append("<dd>Returns the URL(s) currently at this position.</dd>");
        }
        if (byXrefPosition != null){ 
            sb.append("<dt><a href=\"#getXrefByposition\">getXrefByposition</a></dt>");
            sb.append("<dd>Returns the Xref(s) currently at this position.</dd>");
        }
        introduce_IDMapper(sb);
        introduce_IDMapperCapabilities(sb, keys);
        sb.append("</dl>");
        sb.append("</p>");
                
        
        describe_URLMapper(sb, first,  firstMaps, second);            
        describe_IDMapper(sb, first, firstMaps, second);
        describe_IDMapperCapabilities(sb, first, firstMaps, keys);
        describe_byXrefPosition(sb, first);
        describe_getURLByposition(sb, first);
        
        sb.append("</body></html>");
        return Response.ok(sb.toString(), MediaType.TEXT_HTML).build();
    }

    protected final void introduce_Statistics(StringBuilder sb) {
        sb.append("<dt><a href=\"#getMapping\">getMapping</a></dt>");
        sb.append("<dd>Returns a mapping by its id.</dd>");
        sb.append("<dt><a href=\"#getURLMappings\"></a>getURLMappings</dt>");
        sb.append("<dd>Gets a full mapping for a URL and possible target nameSpaces.</dd>");
        sb.append("<dt><a href=\"#getProvenance\"><getProvenance/a></dt>");
        sb.append("<dd>Returns a provenance by its id.</dd>");
        sb.append("<dt><a href=\"#getProvenanceByPosition\">getProvenanceByPosition</a></dt>");
        sb.append("<dd>Returns a provenance by its possition.</dd>");
        sb.append("<dt><a href=\"#getProvenancesByPosition\">getProvenancesByPosition</a></dt>");
        sb.append("<dd>Retruns a list of provenances based on possition and limit.</dd>");
        sb.append("<dt><a href=\"#getSourceProvenanceByNameSpace\">getSourceProvenanceByNameSpace</a></dt>");
        sb.append("<dd>List provenances with this nameSpace as the source.</dd>");
        sb.append("<dt><a href=\"#getTargetProvenanceByNameSpace\">getTargetProvenanceByNameSpace</a></dt>");
        sb.append("<dd>List provenances with this nameSpace as the target.</dd>");
        sb.append("<dt><a href=\"#getDataSourceStatistics\"></a>getDataSourceStatistics</dt>");
        sb.append("<dd>Returns the statistics for a single DataSource by code</dd>");
        sb.append("<dt><a href=\"#getDataSourceStatisticsByAPosition\"></a>getDataSourceStatisticsByAPosition</dt>");
        sb.append("<dd>Returns the statistics for a single DataSouce by position</dd>");
        sb.append("<dt><a href=\"#getDataSourceStatisticsByPosition\">getDataSourceStatisticsByPosition</a></dt>");
        sb.append("<dd>Returns the statistics for a list of DataSources based on postion and limit.</dd>");
    }
    
    

    protected final void introduce_URLMapper(StringBuilder sb) {
        sb.append("<dt><a href=\"#mapByURLs\">mapByURLs</a></dt>");
        sb.append("<dd>List the URLs that map to these URLs</dd>");
        sb.append("<dt><a href=\"#mapByURL\">mapByURL</a></dt>");
        sb.append("<dd>List the URLs that map to this URL</dd>");
        sb.append("<dt><a href=\"#URLExists\">URLExists</a></dt>");
        sb.append("<dd>State if the URL is know to the Mapping Service or not</dd>");
        sb.append("<dt><a href=\"#URLSearch\">URLSearch</a></dt>");
        sb.append("<dd>Searches for URLs that have this ending.</dd>");
    }
    
    protected final void introduce_IDMapper(StringBuilder sb) {
        sb.append("<dt><a href=\"#mapByXRef\">mapByXRef</a></dt>");
        sb.append("<dd>List the Xrefs that map to this Xref</dd>");
        sb.append("<dt><a href=\"#mapByXRefs\">mapByXRefs</a></dt>");
        sb.append("<dd>List the Xrefs that map to these Xrefs</dd>");
        sb.append("<dt><a href=\"#xrefExists\">xrefExists</a></dt>");
        sb.append("<dd>State if the Xref is know to the Mapping Service or not</dd>");       
        sb.append("<dt><a href=\"#freeSearch\">freeSearch</a></dt>");
        sb.append("<dd>Searches for Xrefs that have this id.</dd>");
        sb.append("<dt><a href=\"#getCapabilities\">getCapabilities</a></dt>");
        sb.append("<dd>Gives the Capabilitles as defined by BridgeDB.</dd>");
        sb.append("<dt>Close()</a></dt>");
        sb.append("<dd>Not supported as clients should not be able to close the server.</dd>");
        sb.append("<dt>isConnected</dt>");
        sb.append("<dd>Not supported as Close() is not allowed</dd>");
    }

    protected final void introduce_IDMapperCapabilities(StringBuilder sb, Set<String> keys) {
        sb.append("<dt><a href=\"#isFreeSearchSupported\">isFreeSearchSupported</a></dt>");
        if (idMapper.getCapabilities().isFreeSearchSupported()){
            sb.append("<dd>Returns True as freeSearch and URLSearch are supported.</dd>");
        } else {
            sb.append("<dd>Returns False because underlying IDMappper does not support freeSearch or URLSearch.</dd>");                
        }        
        sb.append("<dt><a href=\"#getSupportedSrcDataSources\">getSupportedSrcDataSources</a></dt>");
        sb.append("<dd>Returns Supported Source (BridgeDB)DataSource(s).</dd>");
        sb.append("<dt><a href=\"#getSupportedTgtDataSources\">getSupportedTgtDataSources</a></dt>");
        sb.append("<dd>Returns Supported Target (BridgeDB)DataSource(s).</dd>");
        sb.append("<dt><a href=\"#isMappingSupported\">isMappingSupported</a></dt>");
        sb.append("<dd>States if two DataSources are mapped at least once.</dd>");
        sb.append("<dt><a href=\"#getProperty\">getProperty</a></dt>");
        if (keys.isEmpty()){
            sb.append("<dd>There are currently no properties supported.</dd>");
        } else {
            sb.append("<dd>Returns The property value for this key.</dd>");
        }
        sb.append("<dt><a href=\"#getKeys\">getKeys</a></dt>");
        if (keys.isEmpty()){
            sb.append("<dd>There are currently no properties supported.</dd>");
        } else {
            sb.append("<dd>Returns The keys and their property value.</dd>");
        }
    }

    protected final void describe_URLMapper(StringBuilder sb, Xref first, Set<Xref> firstMaps, Xref second) throws UnsupportedEncodingException{
        sb.append("<h2>URL based methods");

        describe_mapByURL(sb, first, firstMaps, second);
        describe_mapByURLs(sb, first, firstMaps, second);
        describe_URLExists(sb, first);
        describe_URLSearch(sb, first); 
    }
    
    private void describe_mapByURL(StringBuilder sb, Xref first, Set<Xref> firstMaps, Xref second) throws UnsupportedEncodingException{
        sb.append("<h3><a name=\"mapByURL\">mapByURL</h3>");
            sb.append("<ul>");
            sb.append("<li>List the URLs that map to this URL</li>");
            sb.append("<li>Required arguements:</li>");
                sb.append("<ul>");
                sb.append("<li>srcURL as string</li>");
                sb.append("</ul>");
            sb.append("<li>Optional arguments</li>");
                sb.append("<ul>");
                sb.append("<li>tgtNameSpace as string</li> ");
                    sb.append("<ul>");        
                    sb.append("<li>There can be more than one</li>");        
                    sb.append("<li>This is the NameSpace part of the URLs to mapped to</li>");
                    sb.append("</ul>");
                sb.append("</ul>");
            sb.append("<li>Example: <a href=\"");
                    sb.append(uriInfo.getBaseUri());
                    StringBuilder sbInnerPure = new StringBuilder("mapByURL?srcURL=");
                    StringBuilder sbInnerEncoded = new StringBuilder("mapByURL?srcURL=");
                    sbInnerPure.append(first.getUrl());
                    sbInnerEncoded.append(URLEncoder.encode(first.getUrl(), "UTF-8"));
                    sb.append(sbInnerEncoded.toString());
                    sb.append("\">");
                    sb.append(sbInnerPure.toString());
                    sb.append("</a></li>");    
            sb.append("<li>Example: <a href=\"");
                    sb.append(uriInfo.getBaseUri());
                    for (Xref map:firstMaps){
                        sbInnerPure.append("&tgtNameSpace=");
                        sbInnerPure.append(map.getDataSource().getNameSpace());
                        sbInnerEncoded.append("&tgtNameSpace=");
                        sbInnerEncoded.append(URLEncoder.encode(map.getDataSource().getNameSpace(), "UTF-8"));
                    }
                    sb.append(sbInnerEncoded.toString());
                    sb.append("\">");
                    sb.append(sbInnerPure.toString());
                    sb.append("</a></li>");                    
            sb.append("</ul>");
    }
    
    private void describe_mapByURLs(StringBuilder sb, Xref first, Set<Xref> firstMaps, Xref second) throws UnsupportedEncodingException{
        sb.append("<h3><a name=\"mapByURLs\">mapByURLs</h3>");
            sb.append("<ul>");
            sb.append("<li>List the URLs that map to these URLs</li>");
            sb.append("<li>Required arguements:</li>");
                sb.append("<ul>");
                sb.append("<li>srcURL as string</li>");
                sb.append("<li>There can be more than one</li>");        
                sb.append("</ul>");
            sb.append("<li>Optional arguments</li>");
                sb.append("<ul>");
                sb.append("<li>tgtNameSpace as string</li> ");
                    sb.append("<ul>");        
                    sb.append("<li>There can be more than one</li>");        
                    sb.append("<li>This is the NameSpace part of the URLs to mapped to</li>");
                    sb.append("</ul>");
                sb.append("</ul>");
            sb.append("<li>Example: <a href=\"");
                    sb.append(uriInfo.getBaseUri());
                    StringBuilder sbInnerPure = new StringBuilder("mapByURLs?srcURL=");
                    StringBuilder sbInnerEncoded = new StringBuilder("mapByURLs?srcURL=");
                    sbInnerPure.append(first.getUrl());
                    sbInnerEncoded.append(URLEncoder.encode(first.getUrl(), "UTF-8"));
                    sbInnerPure.append("&srcURL=");
                    sbInnerEncoded.append("&srcURL=");
                    sbInnerPure.append(second.getUrl());
                    sbInnerEncoded.append(URLEncoder.encode(second.getUrl(), "UTF-8"));
                    sb.append(sbInnerEncoded.toString());
                    sb.append("\">");
                    sb.append(sbInnerPure.toString());
                    sb.append("</a></li>");    
            sb.append("<li>Example: <a href=\"");
                    sb.append(uriInfo.getBaseUri());
                    for (Xref map:firstMaps){
                        sbInnerPure.append("&tgtNameSpace=");
                        sbInnerPure.append(map.getDataSource().getNameSpace());
                        sbInnerEncoded.append("&tgtNameSpace=");
                        sbInnerEncoded.append(URLEncoder.encode(map.getDataSource().getNameSpace(), "UTF-8"));
                    }
                    sb.append(sbInnerEncoded.toString());
                    sb.append("\">");
                    sb.append(sbInnerPure.toString());
                    sb.append("</a></li>");                    
            sb.append("</ul>");
    }
    
    private void describe_URLExists(StringBuilder sb, Xref first) throws UnsupportedEncodingException{
        sb.append("<h3><a name=\"URLExists\">URLExists</h3>");
            sb.append("<ul>");
            sb.append("<li>State if the URL is know to the Mapping Service or not</li>");
            sb.append("<li>Required arguements:</li>");
                sb.append("<ul>");
                sb.append("<li>URL as string</li>");
                sb.append("</ul>");
            sb.append("<li>Example: <a href=\"");
                    sb.append(uriInfo.getBaseUri());
                    sb.append("URLExists?URL=");
                    sb.append(URLEncoder.encode(first.getUrl(), "UTF-8"));
                    sb.append("\">");
                    sb.append("URLExists?URL=");
                    sb.append(first.getUrl());
                    sb.append("</a></li>");    
            sb.append("</ul>");
    }
    
    private void describe_URLSearch(StringBuilder sb, Xref first) throws UnsupportedEncodingException{
        sb.append("<h3><a name=\"URLSearch\">URLSearch</h3>");
            sb.append("<ul>");
            sb.append("<li>Searches for URLs that have this ending.</li>");
            sb.append("<li>Required arguements:</li>");
                sb.append("<ul>");
                sb.append("<li>text as string</li>");
                sb.append("</ul>");
            sb.append("<li>Optional arguments</li>");
                sb.append("<ul>");
                sb.append("<li>limit as an Integer </li>");
                sb.append("</ul>");
            sb.append("<li>Example: <a href=\"");
                    sb.append(uriInfo.getBaseUri());
                    sb.append("URLSearch?text=");
                    sb.append(URLEncoder.encode(first.getId(), "UTF-8"));
                    sb.append("&limit=5");
                    sb.append("\">");
                    sb.append("URLSearch?text=");
                    sb.append(first.getId());
                    sb.append("&limit=5");
                    sb.append("</a></li>");    
            sb.append("</ul>");        
    }
    
    protected void describe_IDMapper(StringBuilder sb, Xref first, Set<Xref> firstMaps, Xref second) throws UnsupportedEncodingException{
        sb.append("<h2>Implementations of BridgeDB's IDMapper methods</h2>");

        describe_mapByXRef(sb, first, firstMaps, second);    
        describe_mapByXRefs(sb, first, firstMaps, second);    
        describe_xrefExists(sb, first);
        describe_freeSearch(sb, first);
        describe_getCapabilities(sb); 
        sb.append("<h3>Other IDMapper Functions</h3>");
        sb.append("<dl>");
        sb.append("<dt>Close()</a></dt>");
        sb.append("<dd>Not supported as clients should not be able to close the server.</dd>");
        sb.append("<dt>isConnected</dt>");
        sb.append("<dd>Not supported as Close() is not allowed</dd>");
        sb.append("</dl>");
    }
    
    private void describe_mapByXRef(StringBuilder sb, Xref first, Set<Xref> firstMaps, Xref second) 
            throws UnsupportedEncodingException{
        sb.append("<h3><a name=\"mapByXRef\">mapByXRef</h3>");
            sb.append("<ul>");
            sb.append("<li>Implements:  Set&ltXref&gt mapID (Xref ref, DataSource... tgtDataSources)</li>");
            sb.append("<li>List the Xrefs that map to this Xref</li>");
            sb.append("<li>Required arguements:</li>");
                sb.append("<ul>");
                sb.append("<li>id as string</li>");
                sb.append("<li>code as string (Where code is the SystemCode of the DataSource)</li>");
                sb.append("</ul>");
            sb.append("<li>Optional arguments</li>");
                sb.append("<ul>");
                sb.append("<li>tgtCode as string</li> ");
                    sb.append("<ul>");        
                    sb.append("<li>There can be more than one</li>");        
                    sb.append("<li>Where code is the SystemCode of the DataSource</li>");
                    sb.append("</ul>");
                sb.append("</ul>");
            sb.append("<li>Example: <a href=\"");
                    sb.append(uriInfo.getBaseUri());
                    StringBuilder sbInnerPure = new StringBuilder("mapByXRef?id=");
                    StringBuilder sbInnerEncoded = new StringBuilder("mapByXRef?id=");
                    sbInnerPure.append(first.getId());
                    sbInnerEncoded.append(URLEncoder.encode(first.getId(), "UTF-8"));
                    sbInnerPure.append("&code=");
                    sbInnerEncoded.append("&code=");
                    sbInnerPure.append(first.getDataSource().getSystemCode());
                    sbInnerEncoded.append(URLEncoder.encode(first.getDataSource().getSystemCode(), "UTF-8"));
                    sb.append(sbInnerEncoded.toString());
                    sb.append("\">");
                    sb.append(sbInnerPure.toString());
                    sb.append("</a></li>");    
            sb.append("<li>Example: <a href=\"");
                    sb.append(uriInfo.getBaseUri());
                    for (Xref map:firstMaps){
                        sbInnerPure.append("&tgtCode=");
                        sbInnerEncoded.append("&tgtCode=");
                        sbInnerPure.append(map.getDataSource().getSystemCode());
                        sbInnerEncoded.append(URLEncoder.encode(map.getDataSource().getSystemCode(), "UTF-8"));
                    }
                    sb.append(sbInnerEncoded.toString());
                    sb.append("\">");
                    sb.append(sbInnerPure.toString());
                    sb.append("</a></li>");                    
            sb.append("</ul>");
    }
    
    private void describe_mapByXRefs(StringBuilder sb, Xref first, Set<Xref> firstMaps, Xref second) 
            throws UnsupportedEncodingException{
        sb.append("<h3><a name=\"mapByXRefs\">mapByXRefs</h3>");
            sb.append("<ul>");
            sb.append("<li>List the Xrefs that map to these Xrefs</li>");
            sb.append("<li>Implements:  Map&ltXref, Set&ltXref&gt&lt mapID(Collection<Xref> srcXrefs, DataSource... tgtDataSources)</li>");
            sb.append("<li>Required arguements:</li>");
                sb.append("<ul>");
                sb.append("<li>id as string</li>");
                sb.append("<li>code as string (Where code is the SystemCode of the DataSource)</li>");
                sb.append("<li>(There can be multiple \"id\" and \"code\" values</li>");
                    sb.append("<ul>");
                    sb.append("<li>There must be at least one of each.</li>");                
                    sb.append("<li>There must be the same number of each.</li>");                
                    sb.append("<li>They will be paired by order.</li>");                
                    sb.append("</ul>");
                sb.append("</ul>");
            sb.append("<li>Optional arguments</li>");
                sb.append("<ul>");
                    sb.append("<li>tgtCode as string</li> ");
                    sb.append("<li>Where code is the SystemCode of the DataSource</li>");
                sb.append("</ul>");        
            sb.append("<li>Example: <a href=\"");
                    sb.append(uriInfo.getBaseUri());
                    StringBuilder sbInnerPure = new StringBuilder("mapByXRefs?id=");
                    StringBuilder sbInnerEncoded = new StringBuilder("mapByXRefs?id=");
                    sbInnerPure.append(first.getId());
                    sbInnerEncoded.append(first.getId());
                    sbInnerPure.append("&code=");
                    sbInnerEncoded.append("&code=");
                    sbInnerPure.append(first.getDataSource().getSystemCode());
                    sbInnerEncoded.append(URLEncoder.encode(first.getDataSource().getSystemCode(), "UTF-8"));
                    sbInnerPure.append("&id=");
                    sbInnerEncoded.append("&id=");
                    sbInnerPure.append(second.getId());
                    sbInnerEncoded.append(URLEncoder.encode(second.getId(), "UTF-8"));
                    sbInnerPure.append("&code=");
                    sbInnerEncoded.append("&code=");
                    sbInnerPure.append(second.getDataSource().getSystemCode());
                    sbInnerEncoded.append(URLEncoder.encode(second.getDataSource().getSystemCode(), "UTF-8"));
                    sb.append(sbInnerEncoded.toString());
                    sb.append("\">");
                    sb.append(sbInnerPure.toString());
                    sb.append("</a></li>");    
            sb.append("<li>Example: <a href=\"");
                    sb.append(uriInfo.getBaseUri());
                    for (Xref map:firstMaps){
                        sbInnerPure.append("&tgtCode=");
                        sbInnerEncoded.append("&tgtCode=");
                        sbInnerPure.append(map.getDataSource().getSystemCode());
                        sbInnerEncoded.append(URLEncoder.encode(map.getDataSource().getSystemCode(), "UTF-8"));
                    }
                    sb.append(sbInnerEncoded.toString());
                    sb.append("\">");
                    sb.append(sbInnerPure.toString());
                    sb.append("</a></li>");                    
            sb.append("</ul>");
    }
    
    private void describe_xrefExists(StringBuilder sb, Xref first) throws UnsupportedEncodingException{
        sb.append("<h3><a name=\"xrefExists\">xrefExists</h3>");
            sb.append("<ul>");
            sb.append("<li>Implements:  boolean xrefExists(Xref xref)</li>");
            sb.append("<li>State if the Xref is know to the Mapping Service or not</li>");
            sb.append("<li>Required arguements:</li>");
                sb.append("<ul>");
                sb.append("<li>id as string</li>");
                sb.append("<li>code as string (Where code is the SystemCode of the DataSource)</li>");
                sb.append("</ul>");
            sb.append("<li>Example: <a href=\"");
                    sb.append(uriInfo.getBaseUri());
                    sb.append("xrefExists?id=");
                    sb.append(URLEncoder.encode(first.getId(), "UTF-8"));
                    sb.append("&code=");
                    sb.append(URLEncoder.encode(first.getDataSource().getSystemCode(), "UTF-8"));
                    sb.append("\">");
                    sb.append("xrefExists?id=");
                    sb.append(first.getId());
                    sb.append("&code=");
                    sb.append(first.getDataSource().getSystemCode());
                    sb.append("</a></li>");    
            sb.append("</ul>");
    }
    
    private void describe_freeSearch(StringBuilder sb, Xref first) throws UnsupportedEncodingException{
         sb.append("<h3><a name=\"freeSearch\">freeSearch</h3>");
            sb.append("<ul>");
            sb.append("<li>Searches for Xrefs that have this id.</li>");
            sb.append("<li>Implements:  Set@ltXref@gt freeSearch (String text, int limit)</li>");
            sb.append("<li>Required arguements:</li>");
                sb.append("<ul>");
                sb.append("<li>text as string</li>");
                sb.append("</ul>");
            sb.append("<li>Optional arguments</li>");
                sb.append("<ul>");
                sb.append("<li>limit as an Integer </li>");
                sb.append("</ul>");
            sb.append("<li>Example: <a href=\"");
                    sb.append(uriInfo.getBaseUri());
                    sb.append("freeSearch?text=");
                    sb.append(URLEncoder.encode(first.getId(), "UTF-8"));
                    sb.append("&limit=5");
                    sb.append("\">");
                    sb.append("freeSearch?text=");
                    sb.append(first.getId());
                    sb.append("&limit=5");
                    sb.append("</a></li>");    
            sb.append("</ul>");
    }
    
    private void describe_getCapabilities(StringBuilder sb) {
         sb.append("<h3><a name=\"getCapabilities\">getCapabilities</h3>");
            sb.append("<ul>");
            sb.append("<li>Implements:  IDMapperCapabilities getCapabilities()</li>");
            sb.append("<li>Gives the Capabilitles as defined by BridgeDB.</li>");
            sb.append("<li>Example: <a href=\"");
                    sb.append(uriInfo.getBaseUri());
                    sb.append("getCapabilities\">getCapabilities</a></li>");    
            sb.append("</ul>");
    }
    
    protected void describe_IDMapperCapabilities(StringBuilder sb, Xref first, Set<Xref> firstMaps, Set<String> keys)
            throws UnsupportedEncodingException{
        sb.append("<h2>Implementations of BridgeDB's IDMapperCapabilities methods</h2>");
        describe_isFreeSearchSupported(sb);
        describe_getSupportedDataSources(sb);
        describe_isMappingSupported(sb, first, firstMaps); 
        describe_getProperty(sb, keys);            
        describe_getKeys(sb, keys);
    }
    
    private void describe_isFreeSearchSupported(StringBuilder sb) {
         sb.append("<h3><a name=\"isFreeSearchSupported\">isFreeSearchSupported</h3>");
            sb.append("<ul>");
            sb.append("<li>Implements:  boolean isFreeSearchSupported()</li>");
            if (idMapper.getCapabilities().isFreeSearchSupported()){
                sb.append("<li>Returns True as freeSearch and URLSearch are supported.</li>");
            } else {
                sb.append("<li>Returns False because underlying IDMappper does not support freeSearch or URLSearch.</li>");                
            }
            sb.append("<li>Example: <a href=\"");
                    sb.append(uriInfo.getBaseUri());
                    sb.append("isFreeSearchSupported\">isFreeSearchSupported</a></li>");    
            sb.append("</ul>");
    }
    
    private void describe_getSupportedDataSources(StringBuilder sb) {
         sb.append("<h3><a name=\"getSupportedSrcDataSources\">getSupportedSrcDataSources</h3>");
            sb.append("<ul>");
            sb.append("<li>Implements:  Set@ltDataSource@gt  getSupportedSrcDataSources()</li>");
            sb.append("<li>Returns Supported Source (BridgeDB)DataSource(s).</li>");
            sb.append("<li>Example: <a href=\"");
                    sb.append(uriInfo.getBaseUri());
                    sb.append("getSupportedSrcDataSources\">getSupportedSrcDataSources</a></li>");    
            sb.append("</ul>");
          
         sb.append("<h3><a name=\"getSupportedTgtDataSources\">getSupportedTgtDataSources</h3>");
            sb.append("<ul>");
            sb.append("<li>Implements:  Set@ltDataSource@gt  getSupportedTgtDataSources()</li>");
            sb.append("<li>Returns Supported Target (BridgeDB)DataSource(s).</li>");
            sb.append("<li>Example: <a href=\"");
                    sb.append(uriInfo.getBaseUri());
                    sb.append("getSupportedTgtDataSources\">getSupportedTgtDataSources</a></li>");    
            sb.append("</ul>");
    }
    
    private void describe_isMappingSupported(StringBuilder sb, Xref first, Set<Xref> firstMaps) throws UnsupportedEncodingException{
         sb.append("<h3><a name=\"isMappingSupported\">isMappingSupported</h3>");
            sb.append("<ul>");
            sb.append("<li>States if two DataSources are mapped at least once.</li>");
            sb.append("<li>Implements:  boolean isMappingSupported(DataSource src, DataSource tgt)</li>");
            sb.append("<li>Required arguements:</li>");
                sb.append("<ul>");
                sb.append("<li>source as string (Where source is the SystemCode of the Source DataSource)</li>");
                sb.append("<li>target as string (Where target is the SystemCode of the Target DataSource)</li>");
                sb.append("</ul>");
            sb.append("<li>Example: <a href=\"");
                    sb.append(uriInfo.getBaseUri());
                    sb.append("isMappingSupported?source=");
                    sb.append(first.getDataSource().getSystemCode());
                    sb.append("&target=");
                    sb.append(firstMaps.iterator().next().getDataSource().getSystemCode());
                    sb.append("\">");
                    sb.append("isMappingSupported?source=");
                    sb.append(URLEncoder.encode(first.getDataSource().getSystemCode(), "UTF-8"));
                    sb.append("&target=");
                    sb.append(URLEncoder.encode(firstMaps.iterator().next().getDataSource().getSystemCode(), "UTF-8"));
                    sb.append("</a></li>");    
            sb.append("</ul>");
    }

    private void describe_getProperty(StringBuilder sb, Set<String> keys) throws UnsupportedEncodingException{
         sb.append("<h3><a name=\"getProperty\">getProperty</h3>");
            sb.append("<ul>");
            sb.append("<li>Implements:  String getProperty(String key)</li>");
            sb.append("<li>Returns The property value for this key.</li>");
            sb.append("<li>Required arguements:</li>");
                sb.append("<ul>");
                sb.append("<li>key as String </li>");
                sb.append("</ul>");
            if (keys.isEmpty()){
                sb.append("<li>There are currently no properties supported</li>");
            } else {
                sb.append("<li>Example: <a href=\"");
                        sb.append(uriInfo.getBaseUri());
                        sb.append("getProperty?key=");
                        sb.append(keys.iterator().next());
                        sb.append("\">");
                        sb.append("getProperty?key=");
                        sb.append(URLEncoder.encode(keys.iterator().next(), "UTF-8"));
                        sb.append("</a></li>");    
            }
            sb.append("</ul>");
    }
    
    private void describe_getKeys(StringBuilder sb, Set<String> keys){
         sb.append("<h3><a name=\"getKeys\">getKeys</h3>");
            sb.append("<ul>");
            sb.append("<li>Implements:  Set<String> getKeys()</li>");
            sb.append("<li>Returns The keys and their property value.</li>");
            if (keys.isEmpty()){
                sb.append("<li>There are currently no properties supported</li>");
            } else {
                sb.append("<li>Example: <a href=\"");
                        sb.append(uriInfo.getBaseUri());
                        sb.append("getKeys");
                        sb.append("\">");
                        sb.append("getKeys");
                        sb.append("</a></li>");    
            }
            sb.append("</ul>");
    }
    
    private void describe_byXrefPosition(StringBuilder sb, Xref first) throws UnsupportedEncodingException{
         if (byXrefPosition != null){ 
            sb.append("<h3><a name=\"getXrefByposition\">getXrefByposition</h3>");
                sb.append("<ul>");
                sb.append("<li>Returns the Xref(s) currently at this position.</li>");
                sb.append("<li><b>WARNING:</b> There is no guarantee that the same position will return the same Xref over time.</li>");
                    sb.append("<ul>");
                    sb.append("<li>This method can <b>NOT</b> be used as an Identifier.</li>");
                    sb.append("</ul>");
                sb.append("<li>Used to Implement:  org.bridgedb.XrefIterator</li>");
                sb.append("<li>Required arguements:</li>");
                    sb.append("<ul>");
                    sb.append("<li>position as Integer</li>");
                    sb.append("</ul>");
                sb.append("<li>Optional arguments</li>");
                    sb.append("<ul>");
                    sb.append("<li>code as string (Where code is the SystemCode of the DataSource)</li>");
                    sb.append("<li>limit as an Integer </li>");
                        sb.append("<ul>");
                        sb.append("<li>Will return this number of Xrefs starting at position. (assuming that many remain) </li>");
                        sb.append("</ul>");
                    sb.append("</ul>");
                sb.append("<li>Example: <a href=\"");
                        sb.append(uriInfo.getBaseUri());
                        sb.append("getXrefByposition?position=2");
                        sb.append("\">");
                        sb.append("getXrefByposition?position=2");
                        sb.append("</a></li>");    
                sb.append("<li>Example: <a href=\"");
                        sb.append(uriInfo.getBaseUri());
                        sb.append("getXrefByposition?position=0&limit=2&code=");
                        sb.append(first.getDataSource().getSystemCode());
                        sb.append("\">");
                        sb.append("getXrefByposition?position=0&limit=2&code=");
                        sb.append(URLEncoder.encode(first.getDataSource().getSystemCode(), "UTF-8"));
                        sb.append("</a></li>");    
                sb.append("</ul>");
         }    
    }

    private void describe_getURLByposition(StringBuilder sb, Xref first) throws UnsupportedEncodingException{
         if (byURLPosition != null){ 
            sb.append("<h3><a name=\"getURLByposition\">getURLByposition</h3>");
                sb.append("<ul>");
                sb.append("<li>Returns the URL(s) currently at this position.</li>");
                sb.append("<li><b>WARNING:</b> There is no guarantee that the same position will return the same URL over time.</li>");
                    sb.append("<ul>");
                    sb.append("<li>This method can <b>NOT</b> be used as an Identifier.</li>");
                    sb.append("</ul>");
                sb.append("<li>Used to Implement a URL version of:  org.bridgedb.XrefIterator</li>");
                sb.append("<li>Required arguements:</li>");
                    sb.append("<ul>");
                    sb.append("<li>position as Integer</li>");
                    sb.append("</ul>");
                sb.append("<li>Optional arguments</li>");
                    sb.append("<ul>");
                    sb.append("<li>nameSpace as String </li>");
                    sb.append("<li>limit as an Integer </li>");
                        sb.append("<ul>");
                        sb.append("<li>Will return this number of URLs starting at position. (assuming that many remain) </li>");
                        sb.append("</ul>");
                    sb.append("</ul>");
                sb.append("<li>Example: <a href=\"");
                        sb.append(uriInfo.getBaseUri());
                        sb.append("getURLByposition?position=2");
                        sb.append("\">");
                        sb.append("getURLByposition?position=2");
                        sb.append("</a></li>");    
                sb.append("<li>Example: <a href=\"");
                        sb.append(uriInfo.getBaseUri());
                        sb.append("getURLByposition?position=0&limit=20&nameSpace=");
                        sb.append(URLEncoder.encode(first.getDataSource().getNameSpace(), "UTF-8"));
                        sb.append("\">");
                        sb.append("getURLByposition?position=0&limit=20&nameSpace=");
                        sb.append(first.getDataSource().getNameSpace());
                        sb.append("</a></li>");    
                sb.append("</ul>");
         }        
    }
    
    protected final void describe_Statistics(StringBuilder sb, Xref first, Set<Xref> firstMaps, Xref second) throws UnsupportedEncodingException{
        sb.append("<h2>Extra Methods only avaiable for OpenPhacts Implementation</h2>");
    }

    //getMapping/{id}
    //getURLMappings String URL,List<String> "tgtNameSpace"
    //getProvenance/{id}
    //getProvenanceByPosition position
    //getProvenancesByPosition") position""limit")
    //getSourceProvenanceByNameSpace nameSpace
    //getTargetProvenanceByNameSpace nameSpace
    //getDataSourceStatistics "code"
    //getDataSourceStatisticsByAPosition" position
    //getDataSourceStatisticsByPosition position""limit"
    
    private void describe_getDataSourceStatisticsByPosition(StringBuilder sb, Xref first) throws UnsupportedEncodingException{
         if (byURLPosition != null){ 
            sb.append("<h3><a name=\"getDataSourceStatisticsByPosition\">getDataSourceStatisticsByPosition</h3>");
                sb.append("<ul>");
                sb.append("<li>Returns the Statistics for a DataSourceURL(s) currently at this position.</li>");
                sb.append("<li><b>WARNING:</b> There is no guarantee that the same position will return the same DataSource over time.</li>");
                    sb.append("<ul>");
                    sb.append("<li>This method can <b>NOT</b> be used as an Identifier.</li>");
                    sb.append("</ul>");
                sb.append("<li>Used to Implement a URL version of:  org.bridgedb.XrefIterator</li>");
                sb.append("<li>Required arguements:</li>");
                    sb.append("<ul>");
                    sb.append("<li>position as Integer</li>");
                    sb.append("</ul>");
                sb.append("<li>Optional arguments</li>");
                    sb.append("<ul>");
                    sb.append("<li>nameSpace as String </li>");
                    sb.append("<li>limit as an Integer </li>");
                        sb.append("<ul>");
                        sb.append("<li>Will return this number of URLs starting at position. (assuming that many remain) </li>");
                        sb.append("</ul>");
                    sb.append("</ul>");
                sb.append("<li>Example: <a href=\"");
                        sb.append(uriInfo.getBaseUri());
                        sb.append("getURLByposition?position=2");
                        sb.append("\">");
                        sb.append("getURLByposition?position=2");
                        sb.append("</a></li>");    
                sb.append("<li>Example: <a href=\"");
                        sb.append(uriInfo.getBaseUri());
                        sb.append("getURLByposition?position=0&limit=20&nameSpace=");
                        sb.append(URLEncoder.encode(first.getDataSource().getNameSpace(), "UTF-8"));
                        sb.append("\">");
                        sb.append("getURLByposition?position=0&limit=20&nameSpace=");
                        sb.append(first.getDataSource().getNameSpace());
                        sb.append("</a></li>");    
                sb.append("</ul>");
         }        
    }
}


