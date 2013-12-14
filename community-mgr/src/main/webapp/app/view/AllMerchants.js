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
         }
    },
    autoLoad: true
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
    plugins: [new Ext.grid.plugin.CellEditing({
        clicksToEdit: 1
    })],
    columns: [{
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
        dataIndex: 'status',
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
        text: '修改商户',
        scope: this,
        handler: function(){
        	var data = Ext.getCmp('allmerchants').getSelectionModel().getSelection()[0].data;
        	Ext.Ajax.request({
        	    url: '/mgr/category/modify',
        	    method: 'POST',
        	    params: {
        	    	status: data.status,
        	    	merchantId: data.id,
        	    	categoryId: data.categoryId,
        	    	name: data.name,
        	    	contactPhoneNumber: data.contactPhoneNumber,
        	    	contactAddress: data.contactAddress,
        	    	order: data.order
        	    },
        	    success: function(response){
        	        var text = response.responseText;
        	        Ext.getCmp('allmerchants').store.load();
        	    }
        	});
        }
    }], 
	store: allMerchantStore,
	// paging bar on the bottom
    bbar: Ext.create('Ext.PagingToolbar', {
        store: allMerchantStore,
        displayInfo: true,
        displayMsg: 'Displaying merchants {0} - {1} of {2}',
        emptyMsg: "No merchants to display"
    }),
    title: '所有商户'
});