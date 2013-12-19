Ext.Loader.setConfig({
    disableCaching: true
});
Ext.application({
	name: 'Mgr',
	requires: ['Ext.container.Viewport',
	           'Mgr.view.Navigator'],
	appFolder: 'app',
	models: ['User', 'RealNameAuth', 'Image', 'Feedback', 'MerchantAllField', 'MerchantErrorReport'],
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
                	xtype: 'panel',
                	layout: 'card',
                	activeItem: 0,
                	items: [{
                		xtype: 'merchanttree'
                	},{
                		xtype: 'usersgrid'
                	},{
                		xtype: 'allmerchantsgrid'
                	},{
                		xtype: 'realnameauthgrid'
                	},{
                		xtype: 'imagegrid'
                	},{
                		xtype: 'feedbackgrid'
                	},{
                		xtype: 'merchanterrorreportgrid'
                	}]
                }
            ]
        });
    }
});