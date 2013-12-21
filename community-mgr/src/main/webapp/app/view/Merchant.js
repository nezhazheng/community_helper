Ext.define('Mgr.view.Merchant', {
	extend: 'Ext.tree.Panel',
	alias: 'widget.merchanttree',
	id: 'merchanttree',
    columns: [{
        xtype: 'treecolumn',
        text: 'Name',
        dataIndex: 'name',
        width: 150,
        sortable: true
    }, {
        text: 'Order',
        dataIndex: 'order',
        flex: 1
    }, {
        text: '是否是商户',
        dataIndex: 'leaf',
        flex: 1
    }, {
        text: '审核状态',
        dataIndex: 'status',
        flex: 1
    }, {
        text: '服务是否开启',
        dataIndex: 'serviceEnable',
        flex: 1
    },{
    	text: '类别ID',
    	dataIndex: 'id',
    	flex: 1
    }, {
    	text: '商户ID',
    	dataIndex: 'merchantId',
    	flex: 1
    }, {
    	text: '图片ID',
    	dataIndex: 'iconId',
    	flex: 1,
    	hidden: true
    }],
    tbar: [{
    	xtype: 'button',
        text: '添加类别',
        scope: this,
        handler: function(){
        	var addCategoryWindow = new Mgr.view.AddCategoryWindow();
        	addCategoryWindow.show();
        }
    },{
    	xtype: 'button',
        text: '修改类别',
        scope: this,
        handler: function(){
        	var model = Ext.getCmp('merchanttree').getSelectionModel().getSelection()[0];
        	if(model.leaf) {
        		return;
        	}
        	var modifyCategoryWindow = Ext.create('Mgr.view.ModifyCategoryWindow', {
        		data: model
        	});
        	modifyCategoryWindow.show();
        }
    }, {
		xtype: 'button',
	    text: '删除类别或商户',
	    scope: this,
	    handler: function(){
        	var data = Ext.getCmp('merchanttree').getSelectionModel().getSelection()[0].data;
        	var id = data.leaf ? data.merchantId : data.id;
        	Ext.Ajax.request({
        	    url: '/mgr/category/delete',
        	    method: 'GET',
        	    autoAppendParams: true,
        	    disableCaching:false,
        	    params: {
        	    	id: id,
        	    	isCategory: !data.leaf
        	    },
        	    success: function(response){
        	        Ext.getCmp('merchanttree').store.load();
        	    }
        	});
        }
    }],
	store: 'AllMerchants',
    title: '所有商户',
    rootVisible: false
});