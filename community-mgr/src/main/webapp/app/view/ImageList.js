var imageStore = Ext.create('Ext.data.Store',{
    requires: 'Mgr.model.Image',    
    model: 'Mgr.model.Image',
    pageSize: 20,
    proxy: {
         type: 'ajax',
         url: '/mgr/software/image',
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

Ext.define('Mgr.view.ImageList', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.imagegrid',
	id: 'images',
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
        text: '图片ID',
        dataIndex: 'id',
        width: 150,
        sortable: true
    }, {
        text: '图片类型',
        dataIndex: 'type',
        width: 150
    }, {
        header: '平台',
        dataIndex: 'platform',
        width: 150
    }, {
    	text: '图片URL',
    	dataIndex: 'url',
    	width: 300
    }],
    store: imageStore,
    tbar: [{
    	xtype: 'button',
        text: '添加图片',
        scope: this,
        handler: function(){
        	var addImageWindow = new Mgr.view.AddImageWindow();
        	addImageWindow.show();
        }
    }],
    // paging bar on the bottom
    bbar: Ext.create('Ext.PagingToolbar', {
        store: imageStore,
        displayInfo: true,
        displayMsg: 'Displaying images {0} - {1} of {2}',
        emptyMsg: "No images to display"
    }),
    title: '所有图片'
});