Ext.define('Mgr.model.MerchantAllField', {
	extend: 'Ext.data.Model',
    fields: ['id', 'categoryId', 'name', 'contactPhoneNumber', 'contactAddress', 'order', 'authStatus', 'score', 'scoreUserCount', 'description', 'userId', 'serviceEnable']
});