var userStore = Ext.create('Ext.data.Store',{
    requires: 'Mgr.model.User',    
    model: 'Mgr.model.User',
    pageSize: 20,
    proxy: {
         type: 'ajax',
         url: '/mgr/user',
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
             totalProperty: 'totalResult',
             root: 'list'
         }
    },
    autoLoad: true
});

Ext.define('Mgr.view.UserList', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.usersgrid',
	id: 'users',
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
        text: '用户编号',
        dataIndex: 'id',
        width: 150
    }, {
        text: '注册手机号',
        dataIndex: 'phonenum',
        width: 150
    }, {
        header: '真实姓名',
        dataIndex: 'realName',
        width: 150,
        sortable: true
    }, {
    	text: '住址',
    	dataIndex: 'address',
    	width: 150
    }, {
        text: '渠道',
        dataIndex: 'channel',
        width: 150
    }, {
    	text: 'imei',
    	dataIndex: 'imei',
    	width: 150
    }, {
    	text: '实名认证状态',
    	dataIndex: 'realNameAuthStatus',
    	width: 150
    }],
    store: userStore,
    // paging bar on the bottom
    bbar: Ext.create('Ext.PagingToolbar', {
        store: userStore,
        displayInfo: true,
        displayMsg: 'Displaying users {0} - {1} of {2}',
        emptyMsg: "No users to display"
    }),
    title: '所有用户'
});