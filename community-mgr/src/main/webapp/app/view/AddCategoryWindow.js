Ext.define('Mgr.view.AddCategoryWindow', {
	extend: 'Ext.window.Window',
	alias: 'widget.addcategorywindow',
	id: 'addcategorywindow',
    title: '添加类别',
    height: 400,
    width: 400,
    layout: 'fit',
    items: {
        xtype: 'form',
        url: '/mgr/category/add',
        method: 'GET',
        layout: 'anchor',
        disableCaching:false,
        defaults: {
            anchor: '100%'
        },
        defaultType: 'textfield',
        items: [{
            fieldLabel: 'name',
            name: 'name',
            allowBlank: false
        },{
            fieldLabel: 'order',
            name: 'order',
            allowBlank: false
        },{
            fieldLabel: 'categoryId',
            name: 'categoryId',
            allowBlank: false
        },{
            fieldLabel: 'communityId',
            name: 'communityId',
            allowBlank: false
        }],
        buttons: [{
            text: 'Reset',
            handler: function() {
                this.up('form').getForm().reset();
            }
        }, {
            text: 'Submit',
            formBind: true,
            disabled: true,
            handler: function() {
                var form = this.up('form').getForm();
                if (form.isValid()) {
                    Ext.Ajax.request({
                    	url: '/mgr/category/add',
                        method: 'POST',
                        params: form.getValues(),
                    	disableCaching:false,
                        success: function(form, action) {
                           Ext.getCmp('merchanttree').store.load();
                           Ext.getCmp('addcategorywindow').close();
                        },
                        failure: function(form, action) {
                            Ext.Msg.alert('Failed', action);
                        }
                    });
                }
            }
        }]
    }
});