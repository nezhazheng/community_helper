Ext.define('Mgr.store.AllMerchants',{
	extend: 'Ext.data.TreeStore',
    requires: 'Mgr.model.Merchant',    
    model: 'Mgr.model.Merchant',
    defaultRootId: 0,
    nodeParam: 'categoryId',
    proxy: {
         type: 'ajax',
         url: '/mgr/category',
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
             type: 'json',
             root: 'list'
         }
    },
    autoLoad: true
});