var categoryListStore = Ext.create('Ext.data.Store',{
	fields: [{
        name: 'id',
        mapping: function(raw) {
            var result = raw.id;
            return result;
        }
    }, {
        name: 'name',
        mapping: function(raw) {
            return raw.name;
        }
    }], 
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

Ext.define('Mgr.view.WaitToAuthMerchant', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.waittoauth',
	id: 'waittoauthmerchant',
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
        text: 'Name',
        dataIndex: 'name',
        width: 150
    }, {
        header: 'Order',
        dataIndex: 'order',
        width: 150,
        sortable: true,
        editor: {
            allowBlank: false
        }
    }, {
    	text: '描述',
    	dataIndex: 'description',
    	width: 150
    }, {
        text: '审核状态',
        dataIndex: 'status',
        width: 150
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
    	width: 150
    }],
    tbar: [{
    	xtype: 'button',
        text: '审核',
        scope: this,
        handler: function(){
        	var data = Ext.getCmp('waittoauthmerchant').getSelectionModel().getSelection()[0].data;
        	Ext.Ajax.request({
        	    url: '/mgr/category/audit',
        	    method: 'POST',
        	    params: {
        	    	status: 'VALID',
        	    	merchantId: data.id,
        	    	categoryId: data.categoryId,
        	    	order: data.order
        	    },
        	    success: function(response){
        	        var text = response.responseText;
        	        console.log(text);
        	    }
        	});
        }
    }], 
	store: 'WaitToAuthMerchants',
    title: '所有待审核商户'
});