var categoryListStore = Ext.create('Ext.data.Store',{
	fields: ['id', 'name'], 
    proxy: {
         type: 'ajax',
         url: '/mgr/category/all',
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
             type: 'json'
         }
    },
    autoLoad: true
});

var allMerchantStore = Ext.create('Ext.data.Store',{
    requires: 'Mgr.model.MerchantAllField',    
    model: 'Mgr.model.MerchantAllField',
    pageSize: 20,
    proxy: {
         type: 'ajax',
         url: '/mgr/merchant/all',
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
         },
         writer: {
             type: 'json',
             writeAllFields: true
         }
    },
    autoLoad: false
});

Ext.define('Mgr.view.AllMerchants', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.allmerchantsgrid',
	id: 'allmerchants',
	requires: [
	           'Ext.selection.CellModel',
	           'Ext.grid.*',
	           'Ext.form.field.ComboBox',
	           'Ext.data.*',
	           'Ext.util.*',
	           'Ext.form.*'
	       ],
	frame: true,
	
	initComponent: function() {
		Ext.apply(this, {
			plugins: [new Ext.grid.plugin.CellEditing({
		        clicksToEdit: 1
		    })],
		    columns: [{
		        text: '商户ID',
		        dataIndex: 'id',
		        sortable: true,
		        width: 150
		    },{
		        text: '小区ID',
		        dataIndex: 'communityId',
		        sortable: true,
		        width: 150
		    },{
		        text: '商户名称',
		        dataIndex: 'name',
		        width: 150,
		        editor: {
		            allowBlank: false
		        }
		    }, {
		        header: '顺序',
		        dataIndex: 'order',
		        width: 50,
		        sortable: true,
		        editor: {
		            allowBlank: false
		        }
		    },  {
		    	text: '地址',
		    	dataIndex: 'contactAddress',
		    	width: 150,
		        editor: {
		            allowBlank: false
		        }
		    }, {
		    	text: '联系电话',
		    	dataIndex: 'contactPhoneNumber',
		    	width: 150,
		        editor: {
		            allowBlank: false
		        }
		    }, {
		        text: '审核状态',
		        dataIndex: 'authStatus',
		        width: 150,
		        editor: new Ext.form.field.ComboBox({
		    		typeAhead: true,
		    		store: [
		                    ['VALID','VALID'],
		                    ['NOT_VALID','NOT_VALID']
		                ],
		            triggerAction: 'last'
		    	})
		    }, {
		    	text: '是否可用',
		    	dataIndex: 'serviceEnable',
		    	width: 150,
		        editor: new Ext.form.field.ComboBox({
		    		typeAhead: true,
		    		store: [
		                    ['true','true'],
		                    ['false','false']
		                ],
		            triggerAction: 'last'
		    	})
		    }, {
		    	text: '类别列表',
		    	dataIndex: 'categoryId',
		    	width: 150,
		    	editor: new Ext.form.field.ComboBox({
		    		typeAhead: true,
		            displayField: 'name',
		            valueField: 'id',
		            triggerAction: 'last',
		            store: categoryListStore
		    	})
		    }, {
		    	text: '用户编号',
		    	dataIndex: 'userId',
		    	width: 70
		    }, {
		    	text: '评分',
		    	dataIndex: 'score',
		    	width: 50
		    }, {
		    	text: '评分人数',
		    	dataIndex: 'scoreUserCount',
		    	width: 150
		    }, {
		    	text: '描述',
		    	dataIndex: 'description',
		    	width: 150
		    }],
		    tbar: [{
		    	xtype: 'button',
		        text: '增加商户',
		        scope: this,
		        handler: function(){
		        	var merchant = new Mgr.model.MerchantAllField({
		                name: 'name',
		                categoryId: '0',
		    	    	contactAddress: 'Address',
		    	    	contactPhoneNumber: 'Phone Number',
		    	    	order: 0,
		    	    	authStatus: 'NOT_VALID',
		    	    	categoryId: 0,
		    	    	communityId: 1,
		    	    	serviceEnable: false
		            });
		            
		        	Ext.getCmp('allmerchants').store.insert(0, merchant);
		        }
		    },{
		    	xtype: 'button',
		        text: '提交新增商户',
		        scope: this,
		        handler: function(){
		        	var data = Ext.getCmp('allmerchants').getSelectionModel().getSelection()[0].data;
		        	Ext.Ajax.request({
		        	    url: '/mgr/merchant/add',
		        	    method: 'POST',
		        	    jsonData: data,
		        	    success: function(response){
		        	        Ext.getCmp('allmerchants').store.load();
		        	    }
		        	});
		        }
		    },{
		    	xtype: 'button',
		        text: '修改商户',
		        scope: this,
		        handler: this.onModifyClick
		    },{
		    	xtype: 'button',
		        text: '删除商户',
		        scope: this,
		        handler: this.onRemoveClick
		    },{
	        	xtype: 'button',
	        	text: '删除',
	            scope: this,
	            handler: this.onDeleteClick
	        }], 
			store: allMerchantStore,
			// paging bar on the bottom
		    bbar: Ext.create('Ext.PagingToolbar', {
		        store: allMerchantStore,
		        displayInfo: true,
		        displayMsg: 'Displaying merchants {0} - {1} of {2}',
		        emptyMsg: "No merchants to display"
		    })
		});
		this.callParent();
		
		this.on('show', this.loadStore, this, {
            delay: 1,
            single: true
        });
	},
	
    title: '所有商户',
    
    loadStore: function() {
        this.getStore().loadPage(1);
    },
    
	onRemoveClick: function() {
		var data = Ext.getCmp('allmerchants').getSelectionModel().getSelection()[0].data;
    	Ext.Ajax.request({
    	    url: '/mgr/merchant/delete',
    	    method: 'POST',
    	    params: {
    	    	merchantId: data.id
    	    },
    	    success: function(response){
    	        Ext.getCmp('allmerchants').store.load();
    	    }
    	});
	},
	
	onModifyClick: function() {
    	var data = Ext.getCmp('allmerchants').getSelectionModel().getSelection()[0].data;
    	Ext.Ajax.request({
    	    url: '/mgr/merchant/modify',
    	    method: 'POST',
    	    params: {
    	    	status: data.authStatus,
    	    	merchantId: data.id,
    	    	categoryId: data.categoryId,
    	    	name: data.name,
    	    	contactPhoneNumber: data.contactPhoneNumber,
    	    	contactAddress: data.contactAddress,
    	    	order: data.order,
    	    	serviceEnable: data.serviceEnable
    	    },
    	    success: function(response){
    	        Ext.getCmp('allmerchants').store.load();
    	    }
    	});
    },
    
    onDeleteClick: function(grid, rowIndex) {
    	Ext.Ajax.request({
    	    url: '/mgr/merchant/delete',
    	    method: 'POST',
    	    params: {
    	    	merchantId: grid.dataSource.data.items[rowIndex].data.id,
    	    },
    	    success: function(response) {
    	        var text = response.responseText;
    	        Ext.getCmp('allmerchants').store.load();
    	    }
    	});
    }
});