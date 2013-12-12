Ext.define('Mgr.view.AddImageWindow', {
	extend: 'Ext.window.Window',
	alias: 'widget.addimagewindow',
	id: 'addimagewindow',
    title: '添加图片',
    height: 400,
    width: 400,
    layout: 'fit',
    items: {
        xtype: 'form',
        url: '/mgr/software/image',
        layout: 'anchor',
        disableCaching:false,
        defaults: {
            anchor: '100%'
        },
        defaultType: 'textfield',
        items: [{
            xtype: 'textfield',
            name: 'platform',
            fieldLabel: '平台'
        }, {
            xtype: 'textfield',
            name: 'imageType',
            fieldLabel: '图片类型'
        },{
            xtype: 'filefield',
            id: 'form-file',
            emptyText: 'Select an image',
            fieldLabel: 'Photo',
            name: 'file',
            buttonText: '',
            buttonConfig: {
                iconCls: 'upload-icon'
            }
        }],
        buttons: [{
            text: 'Reset',
            handler: function() {
                this.up('form').getForm().reset();
            }
        }, {
            text: '上传',
            formBind: true,
            disabled: true,
            handler: function() {
                var form = this.up('form').getForm();
                if (form.isValid()) {
                	form.submit({
                        url: '/mgr/software/image',
                        waitMsg: 'Uploading your photo...',
                        success: function(fp, o) {
                            msg('Success', tpl.apply(o.result));
                        }
                    });
                }
            }
        }]
    }
});