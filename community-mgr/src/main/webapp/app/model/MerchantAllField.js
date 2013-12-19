Ext.define('Mgr.model.MerchantAllField', {
	extend: 'Ext.data.Model',
    fields: ['id', 'categoryId','communityId','name', 'contactPhoneNumber', 'contactAddress', 'order', 'authStatus', 'score', 'scoreUserCount', 'description', 'userId', 'serviceEnable']
});