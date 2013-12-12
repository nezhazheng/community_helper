Ext.define('Mgr.store.RealNameAuth',{
	extend: 'Ext.data.Store',
    requires: 'Mgr.model.RealNameAuth',    
    model: 'Mgr.model.RealNameAuth',
    proxy: {
         type: 'ajax',
         url: '/mgr/user/realnameauth',
         extraParams: {
        	 'communityId':1,  
         },
         actionMethods: {
             create: 'POST',
             destroy: 'POST',
             read: 'GET',
             update: 'POST'
         },
         noCache: false,
         autoAppendParams: true,
         reader: {
             type: 'json'
         }
    },
    autoLoad: true
});