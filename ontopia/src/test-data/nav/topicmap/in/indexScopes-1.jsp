<%@ include file='../../sharedHeader.jsp' %>
<%@ include file='../../htmlHeader.jsp' %>

  <tm:server serves="topicmap" topicmapId="opera">
      <tm:indexScopes index="topic">
        <rn:cluster type="item" renderTemplate="text">
	  <rn:display slot="title" object="primary" renderTemplate="text" />
        </rn:cluster>
        <rn:block renderTemplate="content"/>
        <rn:layout itemListStringifierArgs=", "/>      
      </tm:indexScopes>
  </tm:server>

<%@ include file='../../htmlFooter.jsp' %>
