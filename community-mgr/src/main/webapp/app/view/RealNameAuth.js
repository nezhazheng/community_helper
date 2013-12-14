Ext.define('Mgr.view.RealNameAuth', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.realnameauthgrid',
	id: 'realnameauth',
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
        text: '用户编号',
        dataIndex: 'userId',
        width: 150,
        sortable: true
    }, {
        header: '真实姓名',
        dataIndex: 'realName',
        width: 150
    }, {
    	text: '联系地址',
    	dataIndex: 'contractAddress',
    	width: 150
    }, {
        text: '创建时间',
        dataIndex: 'createDate',
        width: 150
    }],
    tbar: [{
    	xtype: 'button',
        text: '认证通过',
        scope: this,
        handler: function(){
        	var data = Ext.getCmp('realnameauth').getSelectionModel().getSelection()[0].data;
        	Ext.Ajax.request({
        	    url: '/mgr/user/realnameauth',
        	    method: 'POST',
        	    params: {
        	    	status: 'ALREADY_AUTH',
        	    	authId: data.id,
        	    	userId: data.userId
        	    },
        	    success: function(response){
        	        Ext.getCmp('realnameauth').store.load();
        	    }
        	});
        }
    }], 
	store: 'RealNameAuth',
    title: '用户实名认证'
});