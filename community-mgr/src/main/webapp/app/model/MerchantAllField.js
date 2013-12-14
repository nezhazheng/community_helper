Ext.define('Mgr.model.MerchantAllField', {
	extend: 'Ext.data.Model',
    fields: ['id', 'categoryId', 'name', 'contactPhoneNumber', 'contactAddress', 'order', 'status', 'score', 'scoreUserCount', 'description', 'userId']
});