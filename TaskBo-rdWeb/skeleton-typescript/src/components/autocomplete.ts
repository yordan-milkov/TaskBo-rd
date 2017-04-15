import { bindable, inject, bindingMode } from 'aurelia-framework';
import $ from 'jquery';

@inject(Element)
export class Autocomplete
{
    @bindable({ defaultBindingMode: bindingMode.twoWay }) value; // value of input
    @bindable loader;
    protected element: any;
    protected suggestionsList: any;
    protected selectedValue: any;
    protected input: any;
    protected _suggestions;

    constructor(element)
    {
        this.element = element;
        this._suggestions = [];
    }

    valueChanged()
    {
        this.selectedValue = undefined;
        if (!this.value || this.value.length < 1) {
            this._suggestions = [];
            this.hideSuggestions();
            return;
        }

        this.getSuggestions(this.value).then(suggestions =>
        {
            this._suggestions = suggestions;
            if (this._suggestions.length && this.input.is(':focus')) {
                this.showSuggestions();
            } else {
                this.hideSuggestions();
            }

        });
    }

    getSuggestions(forValue)
    {
        // logger.debug(`Get suggestions for ${forValue}`);
        return Promise.resolve(this.loader.filter(item =>
            item.name.startsWith(forValue)
            //startsWith(this.getSuggestionValue(item), forValue)
        ));
    }

    attached()
    {
        this.suggestionsList = $('div.autocomplete-suggestion', this.element)
        this.hideSuggestions()
        this.input = $('#autocomplete-input-id', this.element);
    }

    hideSuggestions()
    {
        this.suggestionsList.hide();
    }

    showSuggestions()
    {
        this.suggestionsList.show();
    }

    // startsWith(string, start)
    // {
    //     string  = diacritic.clean(string).toLowerCase();
    //     start   = diacritic.clean(start).toLowerCase()
    //     return string.startsWith(start);
    // }
}