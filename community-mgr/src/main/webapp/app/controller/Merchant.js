Ext.define('Mgr.controller.Merchant',{
	extend: 'Ext.app.Controller',
    views: [
        'Merchant',
        'WaitToAuthMerchant',
        'UserList',
        'RealNameAuth',
        'AddCategoryWindow',
        'ImageList',
        'AddImageWindow'
    ],
	init: function() {
        this.control({
            'viewport > panel': {
                render: this.onPanelRendered
            }
        });
    },
    
    onPanelRendered: function() {
        console.log('The panel was rendered');
    }
});