Ext.define('Mgr.store.WaitToAuthMerchants',{
	extend: 'Ext.data.Store',
    requires: 'Mgr.model.Merchant',    
    model: 'Mgr.model.Merchant',
    proxy: {
         type: 'ajax',
         url: '/mgr/merchant/waittoauth',
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
             root: 'result'
         }
    },
    autoLoad: true
});