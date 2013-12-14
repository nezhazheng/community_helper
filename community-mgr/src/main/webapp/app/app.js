Ext.Loader.setConfig({
    disableCaching: true
});
Ext.application({
	name: 'Mgr',
	requires: ['Ext.container.Viewport',
	           'Mgr.view.Navigator'],
	appFolder: 'app',
	models: ['User', 'RealNameAuth', 'Image', 'Feedback', 'MerchantAllField'],
	controllers: ['Merchant'],
	stores: ['AllMerchants', 'RealNameAuth'],
	launch: function() {
        Ext.create('Ext.container.Viewport', {
            layout: 'border',
            items: [
                {
                	region: 'west',
                	height: '100%',
                	width: 220,
                	layout: 'fit',
                	xtype: 'mgrnavigator'
                },
                {
                	id: 'right-container',
                	region: 'center',
                	xtype: 'container',
                	layout: 'fit',
                	items: [{
                		xtype: 'merchanttree'
                	}]
                }
            ]
        });
    }
});