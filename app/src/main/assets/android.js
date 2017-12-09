function Toast(str,time)
{
//ః㔨ᮉፓ⎥ᏣἹᇺ⏐㤺
app.Toastshow(str,time);
}

function ReadFile(filename)
{
return app.ReadFile(filename);
}

function FileList(filepath)
{
return eval("("+app.FileList(filepath)+")");
}

function WritedFile(filename,content)
{
return app.ReadFile(filename,content);
}

function getJsonObjLength(jsonObj) {

        var Length = 0;

        for (var item in jsonObj) {

            Length++;

        }

        return Length;

}