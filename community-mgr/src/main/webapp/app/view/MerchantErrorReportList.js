var merchantErrorReportStore = Ext.create('Ext.data.Store',{
    requires: 'Mgr.model.MerchantErrorReport',    
    model: 'Mgr.model.MerchantErrorReport',
    pageSize: 20,
    proxy: {
         type: 'ajax',
         url: '/mgr/merchant/errorreport',
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
             root: 'list',
             totalProperty: 'totalResult'
         }
    },
    autoLoad: true
});

Ext.define('Mgr.view.MerchantErrorReportList', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.merchanterrorreportgrid',
	id: 'merchanterrorreport',
	requires: [
	           'Ext.selection.CellModel',
	           'Ext.grid.*',
	           'Ext.form.field.ComboBox',
	           'Ext.data.*',
	           'Ext.util.*',
	           'Ext.form.*',
	           'Ext.PagingToolbar'
	       ],
	frame: true,
    columns: [{
        text: '用户ID',
        dataIndex: 'userId',
        width: 150,
        sortable: true
    }, {
        text: '商户名',
        dataIndex: 'merchantName',
        width: 150
    }, {
        header: '报错类型',
        dataIndex: 'errorCategory',
        width: 150
    }],
    store: merchantErrorReportStore,
    // paging bar on the bottom
    bbar: Ext.create('Ext.PagingToolbar', {
        store: merchantErrorReportStore,
        displayInfo: true,
        displayMsg: 'Displaying images {0} - {1} of {2}',
        emptyMsg: "No images to display"
    }),
    title: '所有商户报错'
});